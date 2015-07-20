# yubikey-login-hook-liferay-6-x
-
It was a project shared by Google Code Hosting Projects.
-

With this hook you can integrate a new Liferay's authentication in your portal. This hook use YubiKey OTP API by Yubico.com

Through the Portal Settings you can choose email + OTP or email + password + OTP authentication way.
The OTP must be registered on the site of a YubiKey Yubico fact for validation in the background are called API Yubico.com.

Each user can authenticate / register using YubiKey one at a time, in fact the DeviceId or also called PublicId is associated with the user at the time of provisioning itself. 
If necessary, the user can authenticate with another YubiKey but the DeviceId must be "new", that is not being used by another user on the same portal.

Small note: If the portal administrator HAD DISABLED FURTHER CONTROL BY PASSWORD, so users can only authenticate with email + OTP, then DeviceId must be the same as last access facts, it does not run an overwriting of DeviceId (operation only possible with user + pwd + OTP)
