<?php

/**

* Reads all the quizzes from the database and returns them as a string.

* @param author kevin.solovjov@gmail.com

*/

require "connectDB.php";
$con = connect(); //Calls the connect method to gain access to Database

$request = $_POST['request'];
$userid  = $_POST['userid'];
$offset  = $_POST['offset'];


//Request == 0 means that the server is being asked for the number of quizzes on an account
if ($request == 0) {
    $checkquiz = mysqli_query($con, "SELECT  *  FROM accountrelation WHERE accountid = '$userid'");
    if (!$checkquiz) {
        printf("Error: %s\n", mysqli_error($con));
        exit();
    } else {
        
        $nr = mysqli_num_rows($checkquiz);
        //Returns the number of rows found in the table, which is the number of quizzes.
        echo $nr;
    }
}


//Request == 1 means the server is being asked for all the quizzes on a specific account, determined by userid, this request is repeated for every quiz on an account with changes to the "offset" variable to read all the quizzes.

else if ($request == 1) {
    //Selects a quiz bound to the chosen account. Quiz chosen depends on offset, which increments every time a request is sent from the application.
    $checkquiz = mysqli_query($con, "SELECT  *  FROM accountrelation WHERE accountid = '$userid' LIMIT 1 OFFSET " . $offset);
    if (!$checkquiz) {
        printf("Error: %s\n", mysqli_error($con));
        exit();
    }
    $results = array();
    while ($data = mysqli_fetch_array($checkquiz)) {
        
        $results = array();
        
        $quizid  = $data['quizid'];
        //Gets the details of found quiz
        $getquiz = mysqli_query($con, "SELECT  *  FROM quiz WHERE quizid = '$quizid'");
        while ($data2 = mysqli_fetch_array($getquiz)) {
            $results[] = $data2;
        }
        
        //Finds all questions for a quiz
        $checkquestions = mysqli_query($con, "SELECT  *  FROM quizrelation WHERE quizid = '$quizid'");
        
        while ($data3 = mysqli_fetch_array($checkquestions)) {
            
            $questionid  = $data3['questionid'];
            //Gets details of the found questions
            $getquestion = mysqli_query($con, "SELECT  *  FROM questions WHERE questionid = '$questionid'");
            
            while ($data4 = mysqli_fetch_array($getquestion)) {
                $results[] = $data4;
            }
            
            
        }
    }
    //Returns the quiz and its question as a JSON object to be read by the application
    echo json_encode($results);
}

//Closes database connection
mysqli_close($con);
?>