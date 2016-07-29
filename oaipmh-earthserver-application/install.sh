cd ../oaipmh
mvn clean install
cd ../oaipmh-catalog-manager-servlet/
mvn clean install

rm -rf /home/john/.tomcat_installation/webapps/oaipmh-catalog-manager-servlet-0.0.1-SNAPSHOT
rm -rf /home/john/.tomcat_installation/webapps/oaipmh-catalog-manager-servlet-0.0.1-SNAPSHOT.war

cp /home/john/workspace/oaipmh-catalog-manager-servlet/target/oaipmh-catalog-manager-servlet-0.0.1-SNAPSHOT.war /home/john/.tomcat_installation/webapps/oaipmh-catalog-manager-servlet-0.0.1-SNAPSHOT.war
