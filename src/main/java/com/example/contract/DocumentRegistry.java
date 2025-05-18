package com.example.contract;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/LFDT-web3j/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.7.0.
 */
@SuppressWarnings("rawtypes")
public class DocumentRegistry extends Contract {
    public static final String BINARY = "6080604052348015600e575f5ffd5b506102cb8061001c5f395ff3fe608060405234801561000f575f5ffd5b506004361061003f575f3560e01c806327258b2214610043578063513b581414610073578063e1fa8e84146100a3575b5f5ffd5b61005d600480360381019061005891906101bf565b6100bf565b60405161006a9190610204565b60405180910390f35b61008d600480360381019061008891906101bf565b6100e4565b60405161009a9190610204565b60405180910390f35b6100bd60048036038101906100b891906101bf565b610100565b005b5f5f5f8381526020019081526020015f205f9054906101000a900460ff169050919050565b5f602052805f5260405f205f915054906101000a900460ff1681565b5f5f8281526020019081526020015f205f9054906101000a900460ff161561015d576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161015490610277565b60405180910390fd5b60015f5f8381526020019081526020015f205f6101000a81548160ff02191690831515021790555050565b5f5ffd5b5f819050919050565b61019e8161018c565b81146101a8575f5ffd5b50565b5f813590506101b981610195565b92915050565b5f602082840312156101d4576101d3610188565b5b5f6101e1848285016101ab565b91505092915050565b5f8115159050919050565b6101fe816101ea565b82525050565b5f6020820190506102175f8301846101f5565b92915050565b5f82825260208201905092915050565b7f4861736820616c726561647920726567697374657265640000000000000000005f82015250565b5f61026160178361021d565b915061026c8261022d565b602082019050919050565b5f6020820190508181035f83015261028e81610255565b905091905056fea2646970667358221220b07c39fea8ad9cff01fc1b7f4e7962270cd90a8402025f6aa4ba7e5c41919af064736f6c634300081e0033";

    private static String librariesLinkedBinary;

    public static final String FUNC_ISREGISTERED = "isRegistered";

    public static final String FUNC_REGISTER = "register";

    public static final String FUNC_REGISTEREDHASHES = "registeredHashes";

    @Deprecated
    protected DocumentRegistry(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected DocumentRegistry(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected DocumentRegistry(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected DocumentRegistry(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<Boolean> isRegistered(byte[] hash) {
        final Function function = new Function(FUNC_ISREGISTERED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(hash)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> register(byte[] hash) {
        final Function function = new Function(
                FUNC_REGISTER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(hash)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> registeredHashes(byte[] param0) {
        final Function function = new Function(FUNC_REGISTEREDHASHES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    @Deprecated
    public static DocumentRegistry load(String contractAddress, Web3j web3j,
            Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new DocumentRegistry(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static DocumentRegistry load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new DocumentRegistry(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static DocumentRegistry load(String contractAddress, Web3j web3j,
            Credentials credentials, ContractGasProvider contractGasProvider) {
        return new DocumentRegistry(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static DocumentRegistry load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new DocumentRegistry(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<DocumentRegistry> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(DocumentRegistry.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<DocumentRegistry> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(DocumentRegistry.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    public static RemoteCall<DocumentRegistry> deploy(Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(DocumentRegistry.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), "");
    }

    @Deprecated
    public static RemoteCall<DocumentRegistry> deploy(Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(DocumentRegistry.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), "");
    }

    private static String getDeploymentBinary() {
        if (librariesLinkedBinary != null) {
            return librariesLinkedBinary;
        } else {
            return BINARY;
        }
    }
}
