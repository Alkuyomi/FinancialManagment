<?php



if(isset($_POST["id"])){
	require_once __DIR__ . '/db_connect.php';
	$response = array();
	$db = new DB_CONNECT();

		mysql_query("SET NAMES utf8");
    $id = $_POST["id"] ;

		$result=mysql_query("SELECT * FROM `invoice` WHERE invoice_id = $id");

		if(mysql_num_rows($result) > 0 ){

			$response["invoices"]=array();

			while($row = mysql_fetch_array($result)){

				$invoice = array();

				$invoice["company"]=$row["company_name"  ];
				$invoice["data"]   =$row["invoice_data"  ];
				$invoice["time"]   =$row["invoice_time"  ];
				$invoice["number"] =$row["invoice_number"];
				$invoice["type"]   =$row["invoice_type"  ];
				$invoice["amount"] =$row["invoice_amount"];
				$invoice["name"]   =$row["invoice_name"  ];
				$invoice["total"]  =$row["invoice_total" ];



				array_push($response["invoices"], $invoice);
			}

			$response['value']=1;

		}else{
			$response['value']=0;
		}



}else{
	$response['value']=-1;
}

echo json_encode($response);
?>
