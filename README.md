GOKbGithubAdapter
=================

This project is a template / demonstration of the GOKB OAI Interface. It's a stand-alone groovy script which
collects updates to the GOKb knowledge base (since the last run) and then commit those changes to a separate
GITHub repository. The idea is to make it easy for publishers to re-consume their own cleaned package lists.

However, this project is really a template for anyone wanting to pull package (Or title, or org) level updates
from GOKb. It should be easily modifyable to only select specific publisher information or perform specific package
tasks.

Developers will need to exercise their own judgement as to the suitabilility of this API over (For example) GITHub 
webhooks attached to the published repository above, or other alternatives.


Publish Packages from GOKb into a git project for change tracking and easy download
