(ns tinklj.streaming
  (:import (com.google.crypto.tink StreamingAead)))

(defn encrypting-channel
  [^StreamingAead streaming-primitive
   ciphertext-destination
   associated-data]
  (.newEncryptingChannel streaming-primitive
                         ciphertext-destination
                         associated-data))

(defn decrypting-channel
  [^StreamingAead streaming-primitive
   ciphertext-source
   associated-data]
  (.newDecryptingChannel streaming-primitive
                         ciphertext-source
                         associated-data))

(defn seekable-decrypting-channel
  [^StreamingAead streaming-primitive
   ciphertextChannel
   associated-data]
  (.newSeekableDecryptingChannel streaming-primitive
                                 ciphertextChannel
                                 associated-data))

(defn decrypting-stream
  [^StreamingAead streaming-primitive
   ciphertextChannel
   associated-data]
  (.newDecryptingStream streaming-primitive
                        ciphertextChannel
                        associated-data))

(defn encrypting-stream
  [^StreamingAead streaming-primitive
   ciphertextChannel
   associated-data]
  (.newEncryptingStream streaming-primitive
                        ciphertextChannel
                        associated-data))

(defn encrypting-channel-write
  [encrypting-channel bytes]
  (.write encrypting-channel bytes))

(defn decrypting-channel-read
  [decrypting-channel bytes]
  (.read decrypting-channel bytes))