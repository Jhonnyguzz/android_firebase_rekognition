## Notas

### Generar el SHA1 Para Firebase

keytool -exportcert -list -v -alias prodkey -keystore <previous_path>\.android\prodkey.keystore
keytool -list -v -keystore <previous_path>\.android\debug.keystore -alias androiddebugkey -storepass android -keypass android

### Autenticacion en Firebase: MVP
https://medium.com/@oscaretnia/implementa-autenticaci%C3%B3n-en-android-con-firebase-mvp-f76fdc0cc785

### Registrar usuarios en Firebase
https://medium.com/@jonathanvazna/firebase-registro-email-password-4468882a3a6f

### Manejar usuarios Firebase:
https://firebase.google.com/docs/auth/web/manage-users?hl=es-419
https://firebase.google.com/docs/auth/users?hl=es-419

### Detectar las caras:
https://firebase.google.com/docs/ml-kit/android/detect-faces
https://medium.com/@carlosgub/ml-kit-detecci%C3%B3n-de-rostros-d5b4c03a3280
https://medium.com/how-to-integrate-aws-rekognition-in-android/aws-rekognition-in-android-9d16f16d591c
https://github.com/aws-amplify/aws-sdk-android/issues/639