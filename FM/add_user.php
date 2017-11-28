<?php

require_once __DIR__ . '/db_connect.php';
$response = array();

if(isset($_POST['name'])){

	$name     = $_POST['name'    ] ;
	$email    = $_POST['email'   ] ;
	$mobile   = $_POST['mobile'  ] ;
	$password = $_POST['password'] ;

	$db = new DB_CONNECT();

	mysql_query("SET NAMES utf8");
	$result=mysql_query("INSERT INTO users(`name`,`email`,`mobile`,`password`) VALUES ('$name','$email','$mobile','$password')");
	if($result){
		$response['value']=1;

	}else{
		$response['value']=0;
	}
}else{
	$response['value']=-1;

}
echo json_encode($response);

?>
