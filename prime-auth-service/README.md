## Swagger UI link
http://127.0.0.1:8668/swagger-ui/index.html
## Create JKS file
### To use keytool to generate a Keystore, you can use the command with the basic structure as follows:
```
keytool -genkeypair -alias <alias> -keyalg <keyalg> -keystore <keystore> -keypass <keypass> -storepass <storepass>
```
Example:
```
keytool -genkeypair -alias primeschool -keyalg RSA -keystore Aa@123456 -keypass Aa@123456 -keystore ~/jwt.jks
```
### Use the following command to export the public key from the generated JKS:
```
keytool -list -rfc --keystore ~/jwt.jks | openssl x509 -inform pem -pubkey -noout
```
### Result export the public key from the generated JKS and save it to file: public.txt
```
-----BEGIN PUBLIC KEY-----
MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApb5XUYZLW+7nxT7mEW/u
sgPmsXam/+qfeHSi5vi2cTtFkIaqtbsgnLi/W40YMD69wxunseBKAUa8AFjDaWZr
WPtyIvhxHfJKq0ayy0+j2/jnmXggbobLwfznm7kEeUwEqUd94xFfhx0LagtwMRHB
1LaKMtlp2SswXzIkUKjz8e+MRvWkX8eRVL22KAE3ZvFwacO4Da7vYXFRVJ+IX/4G
BP19/8Zhf/+6ctCatqeNHzTPJbdV5RI6ZgRonlOQmJxIK7rzyxbSrN2Mk1/u7ODq
EDny2nS8Q6qG31v4s1FgkIZh29KmJTewsTXc+mA/527q0QcNRPzpQlYfp6y569l0
VQIDAQAB
-----END PUBLIC KEY-----
```
## Several important properties are:
```
clientId: client id, required;
clientSecret: client secret;
authorizedGrantTypes: client authorization type, there are 5 modes: authorization_code, password, client_credentials, implicit, refresh_token;
scope: authorization scope;
accessTokenValiditySeconds: access_token valid time, the unit is seconds, the default is 12 hours;
refreshTokenValiditySeconds: refresh_token valid time, the unit is seconds, the default is 30 days;
```
Client information is generally stored in Redis or database, in this case client information is stored in Postgres database;
###  API OAuth2 URL
```
/oauth/authorize: Authorization endpoint
/oauth/token: Get token endpoint
/oauth/confirm_access: User confirm authorization endpoint
/oauth/check_token: Check token endpoint
/oauth/error: Used to render errors in the authorization server
/oauth/token_key: Get the jwt public key endpoint
```
### User default system
```
username: gtsuser, gtsmanager, prime_admin
password: Aa@123456
client_id: clientId
client_secret: Aa@123456
grant_type: password,refresh_token,client_credentials
```

### API OAuth2 Service URL example
#### Generate Token: http://localhost:8668/oauth/token
```
Method: POST
Header:
Content-Type: application/x-www-form-urlencoded

Body request:
[
    "username": "prime_admin",
    "password": "Aa@123456",
    "client_id": "clientId",
    "client_secret": "Aa@123456",
    "grant_type": "password"
]

Body response:
{
    "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NDk1MTUxODgsInVzZXJfbmFtZSI6Imd0c2FkbWluIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiJdLCJqdGkiOiJlNjI3MmMyNS0xYzBjLTQ3N2ItYWE0OS1kYTExMzg2ZjRiNTEiLCJjbGllbnRfaWQiOiJjbGllbnRJZCIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdfQ.jkHWVqildnXMbCYFC2UML6_sTL4pK5NCe031yect3pPHdJWpRDxfCG0_YxATANFw2Ks_duLzfoc6fRTjVjhkUsOqB5SVaUffjB-usx3jAYbLXnAblQRXyAa-coZC38m7-t_QC7-xcjdTY1GeGTlE-YtxtQPJJ4_Zq2pJJbtfIirIdoD2cK6loMsFiUviyWPp2L_WSjCT5vzs223wwjp7SdXYxoObQrZiJFvwoRVPO0Y5kXPBgbnUWdpPzeUYT8IgksEqVw-7NgqmeJayBwvkMm4f7-rYXtRME62MKTux_ffGp2joyKkbsc0Nf-FxDbMDUD8ZIHnVp0GjpaWUglU4Jg",
    "token_type": "bearer",
    "refresh_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJndHNhZG1pbiIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdLCJhdGkiOiJlNjI3MmMyNS0xYzBjLTQ3N2ItYWE0OS1kYTExMzg2ZjRiNTEiLCJleHAiOjE2NTIxMDY4ODgsImF1dGhvcml0aWVzIjpbIlJPTEVfQURNSU4iXSwianRpIjoiMjRiNGRiYmItMDEzOC00YzE2LWEyMDYtYjY5NmFkOGIzYzNiIiwiY2xpZW50X2lkIjoiY2xpZW50SWQifQ.J8UK4fbc95-kXlrnp2wXT7ciUXBsqQTB1Rs0f6uutnKwoQ5WRfAikIooXF8iS4ObcmBF-bnr8xcWYGAqsRc-wEdcbLyfKPBKyFfH1LBJkD5AdOQz8dcgWC1OlDyu_dXuZBlgT5tKSNceK-1H3yYgnytNSmvRPzn1uyhf1GYxzGIlNGk6VRcypwT8p05LjHe0epXuDXGAeXID6oUBtwDMKaTCoxWMMn4c798SBzSJz0bl67d4Frmql3EsMRoeyy7ywaaJmSGF6BCkAAwzNwB_PQhsAiGrPaDLhPdfdb3gRONvyABPFbT0nrnVX3rpOYe5Kff2f4S3C3n0zoByEnU22A",
    "expires_in": 299,
    "scope": "read write",
    "jti": "e6272c25-1c0c-477b-aa49-da11386f4b51"
}
```
##### Note: Decode & Encode JWT token at here: https://jwt.io/ 

#### Check token: http://localhost:8668/oauth/check_token
```
Method: POST
Header:
Content-Type: application/x-www-form-urlencoded

Body request:
[
    "token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NDk1MTYyMzAsInVzZXJfbmFtZSI6Imd0c2FkbWluIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiJdLCJqdGkiOiIwMGFmNGVlZS02OGE5LTRiYTItYWExZi0xNzMxZmJlNGJmN2EiLCJjbGllbnRfaWQiOiJjbGllbnRJZCIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdfQ.G8t21yYTqRTICKr5Dv8NKwFyyVWR42pR5auIvCNBuToMmGlvcHE7hlhWj1MhGHu6KOjKSZ0CB_jQhgcKPU0j5nrh5uD8-H45FJ18ti5JgHcVO3WLWO8LsWQyjunDhv_HY22DuHmp1AUhZtAT6NRBTg3Dwh5PdJ5PAyvqpbxqihj8_ArIlDeb5_rWN6XIwKxKDg1SjmH_jEBFVT37fJnsDgZm7P3AH42l4vjpkySbKD0IpiAzTJRMDVOJ0wVIlOtRSFubIi0u_MY60ruSHoms9npUZxWLNkFNqCoQaEq09OUnNbtBXSMz1z4aoD8AYViAuTQfWb4Wq6wN4-gucHupcA"
]

Body response:
{
    "user_name": "prime_admin",
    "scope": [
        "read",
        "write"
    ],
    "active": true,
    "exp": 1649515878,
    "authorities": [
        "ROLE_ADMIN"
    ],
    "jti": "c1317d61-4b29-4ff1-a26f-3c61b276e981",
    "client_id": "clientId"
}
```

#### Refresh token: http://localhost:8668/oauth/token
```
Method: POST
Header:
Content-Type: application/x-www-form-urlencoded

Body request:
[
    "grant_type": "refresh_token",
    "client_id": "clientId",
    "client_secret": "Aa@123456",
    "refresh_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJndHNhZG1pbiIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdLCJhdGkiOiIwMGFmNGVlZS02OGE5LTRiYTItYWExZi0xNzMxZmJlNGJmN2EiLCJleHAiOjE2NTIxMDc5MzAsImF1dGhvcml0aWVzIjpbIlJPTEVfQURNSU4iXSwianRpIjoiN2YxOWZmMmYtM2Q3Zi00OWJjLTg5MjgtZjFlOWFiODM3YTVjIiwiY2xpZW50X2lkIjoiY2xpZW50SWQifQ.Qo68smgZDIJy4Pw80NtbllaMKGTv89NO6SvBafTmkdv2_tkGMYTB9C65UmRaNdf3S-MGBZYVpsP_ENtEwnmdk4NTXdTAVVPBRP33-4QfN1H6PTrmn8fbya1kSYM63_ikm8ky-kAqGqivK5bhuKkRK3CPPiU3TePGQKO-8zTWMoxVxYJh9ceiYOwuB_3pC6cDFlVF9k7bIVGvgpGO4Z19-weL0B8E0WBrriEEB5kzQMJlJBdjdMouKea8cA3WB_UJckC5B2iOhf37T8p_k0bY8LjtzZmZVeP3B05KQq7n5YVmBeH6Nn3oFFBN0RA1LYasrZDq0d6cv4Gwuvq_RKzw_g"
]

Body response:
{
    "user_name": "prime_admin",
    "scope": [
        "read",
        "write"
    ],
    "active": true,
    "exp": 1649515878,
    "authorities": [
        "ROLE_ADMIN"
    ],
    "jti": "c1317d61-4b29-4ff1-a26f-3c61b276e981",
    "client_id": "clientId"
}
```
