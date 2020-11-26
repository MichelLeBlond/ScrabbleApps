# ScrabbleApps
This project contains a Scrabble game built with an adobe flex front end and a java backend as well as 3 training apps.  The original project used java and flex php and mysql and was build a decade ago. Since then two javascript apps have been added.  angular.htm runs with angular and reads from php, the node version reads directly from mysql. the code has been refactored and Docker files have been added. 

# Source
The refactored source code is included.
-- ScrabbleServletworkspacelocaleclipse is an eclipse project that  generates the war file that will run on a tomcat instance

![TomcatScrabble](https://github.com/MichelLeBlond/ScrabbleApps/blob/main/images/tomcatscrabble.jpg?raw=true)

-- ApacheRoot contains the code needed to run the flex scrabble trainer and the angular trainer. These apps do word searches 
against the scrabble tournament word list.  

This app runs of the root
![Flex Trianer](https://github.com/MichelLeBlond/ScrabbleApps/blob/main/images/flextrainer.jpg?raw=true)

This app runs from /angular.htm
![Flex Trianer](https://github.com/MichelLeBlond/ScrabbleApps/blob/main/images/nodetrainer.jpg?raw=true)

-- FlexSourceScrabbleGame and FlexsourceTrainingApp have the code and ant file for generating the swfs.

-- nodescrabbletrainer has a version of the training app that runs with the database, angular and node rather than php and angular.

# Docker 
  -- The tomcatscrabble app will run on its own.
  -- nodescrabbletrianer and --scrabble-php-app require scrabbledb to be up. It may be neccesary to do a local build if the image is no longer on docker.

# TODO
 -- Because Docker will be automatically retiring images copying images over to GCP containers seems like the next logical action.
  






