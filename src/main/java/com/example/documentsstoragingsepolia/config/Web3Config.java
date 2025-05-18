package com.example.documentsstoragingsepolia.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

@Configuration
public class Web3Config {

    @Value("${eth.rpc-url}")
    private String rpcUrl;

    @Value("${eth.private-key}")
    private String privateKey;

    @Value("${eth.chain-id}")
    private long chainId;

    @Bean
    public Web3j web3j() {
        return Web3j.build(new HttpService(rpcUrl));
    }

    @Bean
    public Credentials credentials() {
        return Credentials.create(privateKey);
    }

    @Bean(name = "web3TransactionManager")
    public TransactionManager transactionManager(Web3j web3j, Credentials credentials) {
        return new RawTransactionManager(web3j, credentials, chainId);
    }
}

