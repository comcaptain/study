```ts
import * as https from 'https';
import * as zlib from 'zlib';

// Helper function to handle Gzip and Deflate decompression
function decompress(buffer: Buffer, encoding: string): Promise<string> {
    return new Promise((resolve, reject) => {
        if (encoding === 'gzip') {
            zlib.gunzip(buffer, (err, decoded) => {
                if (err) {
                    return reject(err);
                }
                resolve(decoded.toString());
            });
        } else if (encoding === 'deflate') {
            zlib.inflate(buffer, (err, decoded) => {
                if (err) {
                    return reject(err);
                }
                resolve(decoded.toString());
            });
        } else {
            // If no compression, return the buffer as a string
            resolve(buffer.toString());
        }
    });
}

// The async function for making a GET request with Gzip handling
export async function gzipGetRequest(url: string, token: string): Promise<string> {
    return new Promise((resolve, reject) => {
        const options: https.RequestOptions = {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,  // Add Authorization header
                'Accept-Encoding': 'gzip, deflate'   // Accept compressed responses
            }
        };

        // Parse the URL and make the request
        https.get(url, options, (res) => {
            const encoding = res.headers['content-encoding'];
            let chunks: Buffer[] = [];

            // Collect response chunks
            res.on('data', (chunk) => {
                chunks.push(chunk);
            });

            // Handle the end of the response
            res.on('end', async () => {
                try {
                    const buffer = Buffer.concat(chunks);
                    const result = await decompress(buffer, encoding || '');
                    resolve(result);  // Return the decompressed content
                } catch (err) {
                    reject(`Decompression error: ${err}`);
                }
            });
        }).on('error', (err) => {
            reject(`Request error: ${err}`);
        });
    });
}

// Example usage
(async () => {
    try {
        const url = 'https://example.com';
        const token = 'your-token-here';
        const result = await gzipGetRequest(url, token);
        console.log(result);  // Decompressed or raw content
    } catch (error) {
        console.error('Error:', error);
    }
})();
```
