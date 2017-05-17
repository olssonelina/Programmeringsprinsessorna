<?php
require "connectDB.php";
$con = connect(); //Anropar och ansluter till db.
$userid = $_POST['userid'];
$friendusername = $_POST['friendusername'];
$request = $_POST['request'];

$response = 0;

if($request == 0){
    
$select = mysqli_query($con, "SELECT  *  FROM account WHERE username = '$friendusername' ");	
$available = mysqli_num_rows($select);

if($available == 0){
    $response = 1;
}
else if($available == 1){
    
    while($data = mysqli_fetch_array($select)){

        $friendid = $data['accountid'];

    }
    
    $select = mysqli_query($con, "SELECT  *  FROM friendrelation WHERE accountid = '$userid' AND friendid = '$friendid' ");
    $available = mysqli_num_rows($select);
    
    if($available == 0){
    
        $sql = "INSERT INTO friendrelation (accountid, friendid) VALUES ('$userid','$friendid')"; 
        mysqli_query($con,$sql);
        $response = 3;
    }
    else{
        $response = 2;
    }


   
}


}
else if($request == 1){
    
     $select = mysqli_query($con, "SELECT  *  FROM friendrelation WHERE accountid = '$userid'");
    
if (!$select) {
    printf("Error: %s\n", mysqli_error($con));
    exit();
}    
    
$results = array();
    while($data = mysqli_fetch_array($select)){
        
       $results[] = $data['friendid']; 

    
}
    $results2  = array();
    foreach ($results as &$value) {
    $select2 = mysqli_query($con, "SELECT * FROM account WHERE accountid = '$value'");
        if (!$select2) {
    printf("Error: %s\n", mysqli_error($con));
    exit();
}
    while($data2 = mysqli_fetch_array($select2)){
        
       $results2[] = $data2; 

    
}
}
    
   $response = json_encode($results2);
    
}




 echo $response;
  mysqli_close($con);
?>