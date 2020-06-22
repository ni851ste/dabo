# Dabo README #


Current Master Buildstatus

[![Build Status](https://travis-ci.com/NiggiSchiggi/dabo.svg?token=v4zBkZ1p2YHbnsqpZVb3&branch=master)](https://travis-ci.com/NiggiSchiggi/dabo)

###Setting Up New IntelliJ Project

* clone project
* File -> New -> Project from existing sources -> choose dabo/ directory
######Import Project - Dialog:
* click on radio button "Create project from existing sources" -> next -> next -> finish

#####Add Modules
* strg + shift + alt + S

######Add backend module
* Modules: click on '+' -> import module
* double click on dabo/backend/build.sbt

######Add frontend module
* Modules: click on '+' -> import module
* double click on dabo/frontend directory
* click on Apply -> OK

Don't forget to wait for sbt to import backend dependencies and to run `npm install` in the frontend