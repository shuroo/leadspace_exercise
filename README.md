Here is my solution to the Leadspace exercise.

The Base-cloud-url for this project is here:  https://leadspace-exercise.herokuapp.com/

The following 5 endpoints are supported:



Categorize  - Performs phrase analysis as described in the exercise.                                                                                                              Syntax:  GET , URL:{{base_url}}//categorize/phrase={{some_phrase}}


          Example: https://leadspace-exercise.herokuapp.com/categorize/phrase=President+And+Test+Shiri+Marketing


         Curl Command:     curl -X GET \'https://leadspace-exercise.herokuapp.com/categorize?phrase=president%20And%20Test%20Shiri%20Marketing' \

 -H 'cache-control: no-cache' \

 -H 'postman-token: 9c7c1ba4-07ab-bc77-4d47-e481ef5db1f6'


A few words regarding the algorithm implementation:  


The algorithm prefers longer phrases over shorter , to make sure sub-phrases like "President" will not be included in the result when a longer phrase ("Vice President") is in the dictionary. 




Hence the algorithm scans the given phrase, extracts the longest term found both in the phrase and in the dictionary on each step, adds it to the result and remove it from the scanned phrase to scan the rest.




The Algorithm is case sensitive. Meaning: If "President" is in the dictionary, "president" will not be recognized or returned in any way due to the lower-case "p" letter. 


The endpoints supported are:



Replace Dictionary content by text file: - The data dictionary is initiated with 8 default values as appear in the exercise description ("President", "Vice President", "CTO", "IT", "CFO", "Sales","Marketing", "Banking" ).                                                                                                                      The ablity to override these values of the data-dictionary is given in this endpoint and in the next. one can upload a non-empty text file and replace the dictionary content at once. This is a POST command. 


        

          Syntax: POST ,  URL:  https://leadspace-exercise.herokuapp.com/replace_dictionary , Body: text file attached as binary content.


         Curl command:   curl -X POST \

     'https://leadspace-exercise.herokuapp.com/replace_dictionary?fileName=sample_data_dictionary.txt' \

        -H 'cache-control: no-cache' \

        -H 'postman-token: eab2269f-84dd-ed33-90e0-c46d6685e3de'


         3.Replace Dictionary content by text file:  There is also a 'GET' endpoint used to Replace data-dictionary by local-file exists (in        advance) on the server:


        Syntax: {{base_url}}/replace_dictionary?fileName={{fileName}}


       Working Example: https://leadspace-exercise.herokuapp.com/replace_dictionary?fileName=sample_data_dictionary.txt


      Curl Command:  curl -X GET \

 'https://leadspace-exercise.herokuapp.com/replace_dictionary?fileName=sample_data_dictionary.txt' \

 -H 'cache-control: no-cache' \

 -H 'postman-token: bcb9226c-5cf3-7503-764a-71f1c523c549'



Reset Dictionary: Resets the data-dictionary back to its original 8 values described in section 2.


        Syntax: GET , {{base-url}}/reset_dictionary

        Working Example: https://leadspace-exercise.herokuapp.com/reset_dictionary

        Curl Command:   curl -X GET \

 https://leadspace-exercise.herokuapp.com/reset_dictionary \

 -H 'cache-control: no-cache' \

 -H 'postman-token: ced1fbe2-bfa5-5b69-e0ed-3872a4fe01cc'



     5.List Dictionary Content: Display all words in current data dictionary as list.


     Syntax:   GET  , url: https://leadspace-exercise.herokuapp.com/list_dictionary_content

     Curl Command:      curl -X GET \

 https://leadspace-exercise.herokuapp.com/list_dictionary_content \

 -H 'cache-control: no-cache' \

 -H 'postman-token: 6ca46123-a2f7-afc2-9da7-e8d1ffdb09aa'





