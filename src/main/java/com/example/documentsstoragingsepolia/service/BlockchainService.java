package com.example.documentsstoragingsepolia.service;

import com.example.contract.DocumentRegistry;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.gas.DefaultGasProvider;

import java.nio.file.Files;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class BlockchainService {

    private final Web3j web3j;
    private final Credentials credentials;

    private static final Path CONTRACT_ADDRESS_PATH = Path.of("contract-address.txt");
    private DocumentRegistry contract;

    @PostConstruct
    public void init() throws Exception {
        String contractAddress;
        if (Files.exists(CONTRACT_ADDRESS_PATH)) {
            // 📥 Загружаем адрес из файла
            contractAddress = Files.readString(CONTRACT_ADDRESS_PATH).trim();
            System.out.println("Loaded existing contract at address: " + contractAddress);
            contract = DocumentRegistry.load(contractAddress, web3j, credentials, new DefaultGasProvider());
        } else {
            // 🚀 Деплоим контракт
            contract = DocumentRegistry.deploy(web3j, credentials, new DefaultGasProvider()).send();
            contractAddress = contract.getContractAddress();
            System.out.println("Deployed new contract at address: " + contractAddress);

            // 💾 Сохраняем адрес в файл
            Files.writeString(CONTRACT_ADDRESS_PATH, contractAddress);
        }
    }

    public void registerHash(byte[] sha256) throws Exception {
        if (sha256.length != 32) {
            throw new IllegalArgumentException("Hash must be exactly 32 bytes (SHA-256)");
        }
        var tx = contract.register(sha256).send();
        System.out.println("TX Hash: " + tx.getTransactionHash());
    }

    public boolean isHashRegistered(byte[] sha256) throws Exception {
        if (sha256.length != 32) {
            throw new IllegalArgumentException("Hash must be exactly 32 bytes (SHA-256)");
        }
        return contract.isRegistered(sha256).send();
    }
}
