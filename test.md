```ts
const fetch = require('node-fetch');
const zlib = require('zlib');

// Example URL that returns a compressed response
const url = 'https://example.com';

// Fetch the URL with decompression handling
async function fetchWithDecompression(url) {
    try {
        const response = await fetch(url, {
            headers: {
                'Accept-Encoding': 'gzip, deflate'  // Let the server know you accept compressed responses
            }
        });

        // Check the encoding of the response
        const encoding = response.headers.get('content-encoding');

        // Stream the response data
        if (encoding === 'gzip') {
            const gunzip = zlib.createGunzip();
            response.body.pipe(gunzip);

            let decompressedData = '';
            gunzip.on('data', (chunk) => {
                decompressedData += chunk.toString();
            });

            gunzip.on('end', () => {
                console.log(decompressedData);  // Output decompressed content
            });

        } else if (encoding === 'deflate') {
            const inflate = zlib.createInflate();
            response.body.pipe(inflate);

            let decompressedData = '';
            inflate.on('data', (chunk) => {
                decompressedData += chunk.toString();
            });

            inflate.on('end', () => {
                console.log(decompressedData);  // Output decompressed content
            });

        } else {
            // If not compressed, directly read the response
            let responseData = '';
            response.body.on('data', (chunk) => {
                responseData += chunk.toString();
            });

            response.body.on('end', () => {
                console.log(responseData);  // Output uncompressed content
            });
        }
    } catch (error) {
        console.error('Fetch error:', error);
    }
}

// Example usage
fetchWithDecompression(url);
```
