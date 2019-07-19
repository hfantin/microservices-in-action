cd /tmp/
curl –k-LO "http://download.oracle.com/otn-pub/java/jce/8/jce_policy-8.zip"
# https://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html
-H 'Cookie: oraclelicense=accept-securebackup-cookie' && unzip
jce_policy-8.zip
rm jce_policy-8.zip
yes |cp -v /tmp/UnlimitedJCEPolicyJDK8/*.jar /usr/lib/jvm/java-1.8-openjdk/jre/lib/security/
