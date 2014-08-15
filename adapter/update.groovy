#!/usr/bin/groovy

// @GrabResolver(name='es', root='https://oss.sonatype.org/content/repositories/releases')
@GrabResolver(name='kint', root='http://projects.k-int.com/nexus-webapp-1.4.0/content/repositories/releases')
@Grapes([
  @Grab(group='net.sf.opencsv', module='opencsv', version='2.0'),
  @Grab(group='org.apache.httpcomponents', module='httpmime', version='4.1.2'),
  @Grab(group='org.apache.httpcomponents', module='httpclient', version='4.0'),
  @Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.5.0'),
  @Grab(group='org.apache.httpcomponents', module='httpmime', version='4.1.2'),
  @Grab(group='com.k-int', module='goai', version='1.0.2'),
  @Grab(group='org.ajoberstar', module='grgit', version='0.3.0') // Updated from 0.2.3
])


import groovy.util.slurpersupport.GPathResult
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*
import groovyx.net.http.*
import org.apache.http.entity.mime.*
import org.apache.http.entity.mime.content.*
import java.nio.charset.Charset
import org.apache.http.*
import org.apache.http.protocol.*
import org.apache.log4j.*
import au.com.bytecode.opencsv.CSVReader
import java.text.SimpleDateFormat
import com.k_int.goai.*;
import org.ajoberstar.grgit.*

def config_file = new File('GOKbGitBridge-config.groovy')

def config = new ConfigSlurper().parse(config_file.toURL())
if ( ! config.maxtimestamp ) {
  println("Intialise timestamp");
  config.maxtimestamp = 0
}


println("git repository:${config.gitrepo}, max timestamp: ${config.maxtimestamp}");

if ( config.gitrepo == null ) {
  println("No configuration - please see GOKbGitBridge-demo-config.groovy, and copyedit to GOKbGitBridge-config.groovy");
  System.exit(1)
}


def checkout_dir = new File('./checkout');

def grgit = null
if ( checkout_dir.exists() ) {
  if ( checkout_dir.isDirectory() ) {
    grgit = Grgit.open('./checkout')
  }
  else {  
    System.exit(1);
  }
}
else {
  // Clone the repository
  grgit = Grgit.clone(dir: new File('./checkout'), uri: config.gitrepo)
}

OaiClient oaiclient = new OaiClient(host:'https://gokb.k-int.com/gokb/oai/packages');
println("Starting...");

oaiclient.getChangesSince(null, 'gokb') { pkg ->
  println("Processing package.... name: ${pkg.metadata.gokb.package.name.text()}");
  // def package_file = new File("./checkout/pkg
}

println("Done.");

config_file.withWriter { writer ->
  config.writeTo(writer)
}
