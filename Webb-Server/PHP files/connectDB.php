<?php

/**

* Database connection

* 

* Function to connect to the database*

* @param author kevin.solovjov@gmail.com

*/

?>
    <?php
function connect(){
$con = mysqli_connect("localhost","id1452038_qwalkadmin","programmeringsprinssessorna123","id1452038_qwalkbase");
mysqli_set_charset($con,'utf8'); //sÃ¤tter teckenkod utf8
return $con;
}
?>