// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.24;

contract DocumentRegistry {
    mapping(bytes32 => bool) public registeredHashes;

    function register(bytes32 hash) public {
        require(!registeredHashes[hash], "Hash already registered");
        registeredHashes[hash] = true;
    }

    function isRegistered(bytes32 hash) public view returns (bool) {
        return registeredHashes[hash];
    }
}
