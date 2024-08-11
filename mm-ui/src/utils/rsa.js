import { JSEncrypt } from 'jsencrypt'
const PUBLIC_KEY = `MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALdQbd4NDzDsT8Bd8rjDp4QH2pm1qBlR
rWyV+Fbx7UMHnvpknWFhgqP4RoQLvI3MtMkhCTDqEx1ikDe7agylo9cCAwEAAQ==`

// 加密
export function Encrypt(data) {
    const encrypt = new JSEncrypt()
    encrypt.setPublicKey(PUBLIC_KEY)
    return encrypt.encrypt(data)
}
