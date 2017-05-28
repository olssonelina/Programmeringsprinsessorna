<?php
/**

* Saves quizzes in database

* @param author kevin.solovjov@gmail.com

*/
require "connectDB.php";
$con    = connect(); //Anropar och ansluter till db.
$finish = $_POST['finish'];

//Finish == 0 means the application is asking to add a question. In the application this happens in a loop until every question is added.
if ($finish == 0) {
    $questionid    = $_POST['questionid'];
    $description   = $_POST['description'];
    $option1       = $_POST['option1'];
    $option2       = $_POST['option2'];
    $option3       = $_POST['option3'];
    $option4       = $_POST['option4'];
    $questiontype  = $_POST['questiontype'];
    $longitude     = $_POST['longitude'];
    $latitude      = $_POST['latitude'];
    $correctanswer = $_POST['correctanswer'];
    
    //If the question ID is -1, it means that the question did not have a previous ID and is new, and shall therefore be added from scratch.
    if ($questionid == -1) {
        $sql = "INSERT INTO questions (description, option1, option2, option3, option4, longitude, latitude, correctanswer, questiontype) VALUES ('$description', '$option1', '$option2', '$option3', '$option4', '$longitude', '$latitude', '$correctanswer', '$questiontype')";
        
        
        mysqli_query($con, $sql);
        
        //Selects the last added question.        
        $savedquestion = mysqli_query($con, "SELECT * FROM questions ORDER BY questionid DESC LIMIT 0,1");
        
        while ($data = mysqli_fetch_array($savedquestion)) {
            
            //Sends the question id back to the application.
            echo $data['questionid'];
        }
    }
    //If the Question ID isn't -1, that means that it is an old question that already exists, and should instead be updated.    
    else {
        $sql = "UPDATE questions SET description='$description', option1 = '$option1', option2 = '$option2', option3 = '$option3', option4 = '$option4', longitude = '$longitude', latitude = '$latitude', correctanswer = '$correctanswer', questiontype = '$questiontype' WHERE questionid='$questionid'";
        
        mysqli_query($con, $sql);
        
        echo -3;
    }
    
}

//Finish == 1 means the application is asking to create a quiz to bind all the previously saved questions to.
else if ($finish == 1) {
    $questionid      = $_POST['questionid'];
    $quizid          = $_POST['quizid'];
    $quizdescription = $_POST['quizdescription'];
    $quiztitle       = $_POST['title'];
    $questionidarray = $_POST['questionidarray'];
    $userid          = $_POST['userid'];
    
    //If  $quizid == -1, it means that the quiz has no previous ID and is new.   
    if ($quizid == -1) {
        //Checks if a quiz with the same title already exists, to prevent a user from having a list of a bunch of different quizzes with no way to distinguish them.
        $checktaken = mysqli_query($con, "SELECT  *  FROM quiz WHERE title = '$quiztitle'");
        $nr         = mysqli_num_rows($checktaken);
        
        //If $nr == 0, it means no quiz with the chosen title was found, and the quiz can be added to the database.
        if ($nr == 0) {
            
            
            $sql = "INSERT INTO quiz (description, title) VALUES ('$quizdescription', '$quiztitle')";
            
            mysqli_query($con, $sql);
            
            //Gets the newly created quiz
            $savedquiz = mysqli_query($con, "SELECT  *  FROM quiz WHERE description = '$quizdescription' AND title = '$quiztitle'");
            
            while ($data = mysqli_fetch_array($savedquiz)) {
                
                $quizid = $data['quizid'];
                
            }
            
            //Gets the list of question IDs, these are saved as a JSON string, so it is first turned into an array.
            $jsonArrayQuestionId = json_decode($questionidarray, TRUE);
            $jsonArrayLength     = count($jsonArrayQuestionId);
            
            //For every ID in the list, it is added to the relation table för quizzes and questions with the quiz ID and the chosen question ID.
            for ($x = 0; $x < $jsonArrayLength; $x++) {
                
                $sql = "INSERT INTO quizrelation (quizid, questionid) VALUES ('$quizid', '$jsonArrayQuestionId[$x]')";
                
                mysqli_query($con, $sql);
                
            }
            
            //The quiz itself is put in a relation table over quizzes and accounts with the quiz ID and the ID of the user who created the quiz.
            $sql = "INSERT INTO accountrelation (quizid, accountid) VALUES ('$quizid', '$userid')";
            mysqli_query($con, $sql);
            
            
            echo '0';
        } else {
            echo '-1';
        }
        //If quiz ID isn't -1, it means the quiz already exists and should be updated instead.
    } else {
        $sql = "UPDATE quiz SET description='$quizdescription', title = '$quiztitle' WHERE quizid='$quizid'";
        mysqli_query($con, $sql);
        
    
        $jsonArrayQuestionId = json_decode($questionidarray, TRUE);
        $jsonArrayLength     = count($jsonArrayQuestionId);
        
        for ($x = 0; $x < $jsonArrayLength; $x++) {
            
            $sql = "INSERT INTO quizrelation (quizid, questionid) VALUES ('$quizid', '$jsonArrayQuestionId[$x] > 0')";
            if ($jsonArrayQuestionId[$x] > 0) {
                
                
                
                
                mysqli_query($con, $sql);
            }
        }
        
        echo '-2';
        
    }
}


mysqli_close($con);
?>