# Doc Red 

## Instalación

* Con AndroidStudio 3.5.1 abra el proyecto y ejecute Gradle para descargar dependencias
* Escriba su Access Id y Secret Key de AWS en el Archivo AWSAsyncTask (Recuerde que el servicio genera costo)
* Luego corra la aplicación en un dispositivo móvil o genere el APK e instálelo en el teléfono
* Necesita conexión a internet

## Uso

1. Para registrarse es necesario escribir un correo válido y una contraseña que coincida de longitud mínima 8 caracteres, de lo contrario Firebase no permite el registro
2. Debe tomarle una foto a la foto de la cédula y una selfie, las dos fotos son necesarias, solo se puede visualizar una al tiempo pero las dos cargan
3. Click en registrar, si AWS puede validar que las dos fotos son la misma persona, Firebase procede con el registro
4. El botón de login es para la autenticación, si el usuario está registrado lo devolverá a la actividad principal de lo contrario se mostrara un mensaje de error
5. Los Toast lo guiarán en el flujo


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
