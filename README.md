# tinklj
A clojure api for [Google Tink](https://github.com/google/tink) cryptographic library

# Symmetric Encryption

The best way to find the user cases for the encryption techniques is to check the [Acceptance Tests](https://github.com/perkss/tinklj/blob/master/test/tinklj/acceptance/symmetric_key_encryption_test.clj). 

First we need to call register to register the encryption techniques. 

<code>(register)</code>
    
Then we are required to generate a keyset-handle and specify the type.    
<code>(keyset-handles/generate-new keyset-handles/AES128-GCM)</code>

We then get the primitive of the keyset-handle and can use this to encrypt and decypt.
<code>(keyset-handles/get-primitive keyset-handle)</code>

<code>(encrypt aead (.getBytes data-to-encrypt) aad)</code>
<code>(decrypt aead encrypted aad)</code>

# Contributions

Please feel free to pick up and issue or create issues and contribute. 

# Contributors

Matt @lamp

Chris @minimal

Stuart @perkss
