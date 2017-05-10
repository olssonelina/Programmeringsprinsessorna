<?php
require "connectDB.php";
$con = connect(); //Anropar och ansluter till db.
$request = $_POST['request'];
$userid = $_POST['userid'];
$offset = $_POST['offset'];

if($request == 0){
$checkquiz = mysqli_query($con, "SELECT  *  FROM accountrelation WHERE accountid = '$userid'");
if (!$checkquiz) {
    printf("Error: %s\n", mysqli_error($con));
    exit();
}
else{
 
$nr = mysqli_num_rows($checkquiz);
    echo $nr;
}   
}

else if($request == 1){
     $checkquiz = mysqli_query($con, "SELECT  *  FROM accountrelation WHERE accountid = '$userid' LIMIT 1 OFFSET ".$offset);
if (!$checkquiz) {
    printf("Error: %s\n", mysqli_error($con));
    exit();
}    
$results = array();
    while($data = mysqli_fetch_array($checkquiz)){
        
        $results = array();
       
        $quizid = $data['quizid'];    
        $getquiz = mysqli_query($con, "SELECT  *  FROM quiz WHERE quizid = '$quizid'");
        while($data2 = mysqli_fetch_array( $getquiz)){
             $results[] = $data2;
        }
        
        $checkquestions = mysqli_query($con, "SELECT  *  FROM quizrelation WHERE quizid = '$quizid'");
        
        while($data3 = mysqli_fetch_array($checkquestions)){
            
                $questionid = $data3['questionid']; 
                $getquestion = mysqli_query($con, "SELECT  *  FROM questions WHERE questionid = '$questionid'");

                while($data4 = mysqli_fetch_array($getquestion)){
                $results[] =$data4;
                } 

    
}
    }
    echo json_encode($results);
}

    
  mysqli_close($con);
?>