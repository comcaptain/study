```ts
const algorithm = 'aes-256-cbc'; // Encryption algorithm
const key = crypto.randomBytes(32); // Secret key (32 bytes for aes-256)
const iv = crypto.randomBytes(16);  // Initialization vector (IV, 16 bytes)

/**
 * Encrypts a text using AES-256-CBC.
 * @param text - The plaintext to encrypt
 * @returns The encrypted data in hexadecimal format
 */
function encrypt(text: string): { iv: string; encryptedData: string } {
    const cipher = crypto.createCipheriv(algorithm, key, iv); // Create cipher with key and IV
    let encrypted = cipher.update(text, 'utf8', 'hex'); // Encrypt the text
    encrypted += cipher.final('hex'); // Finalize encryption

    return {
        iv: iv.toString('hex'),
        encryptedData: encrypted,
    };
}

/**
 * Decrypts an encrypted text using AES-256-CBC.
 * @param encryptedData - The encrypted text (in hexadecimal format)
 * @param iv - The initialization vector (hexadecimal)
 * @returns The decrypted plaintext
 */
function decrypt(encryptedData: string, iv: string): string {
    const decipher = crypto.createDecipheriv(algorithm, key, Buffer.from(iv, 'hex')); // Create decipher with key and IV
    let decrypted = decipher.update(encryptedData, 'hex', 'utf8'); // Decrypt the text
    decrypted += decipher.final('utf8'); // Finalize decryption

    return decrypted;
}

// Example Usage
const plaintext = 'Hello, TypeScript!';
const encrypted = encrypt(plaintext);
console.log('Encrypted:', encrypted);

const decrypted = decrypt(encrypted.encryptedData, encrypted.iv);
console.log('Decrypted:', decrypted);
```
