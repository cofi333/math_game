<?php

header('Access-Control-Allow-Origin: *');
header("Access-Control-Allow-Credentials: true ");
header('Access-Control-Allow-Methods: GET, POST');
header("Access-Control-Allow-Headers: X-Requested-With");
header("Content-type: application/json; charset=UTF-8");
require_once 'config.php';
require_once 'functions.php';

$method = strtolower($_SERVER["REQUEST_METHOD"]);

if (!in_array($method, $methods)) {
    http_response_code(405);
    header("Allow: GET, POST, PATCH, DELETE");
    echo json_encode([
        "message" => "$method is not allowed!"
    ]);
    exit();
}

$table = $_GET['table'] ?? "";
$id = $_GET['id'] ?? 0;

if (!in_array($table, $tables)) {
    http_response_code(404);

    echo json_encode([
        "message" => "Invalid request!"
    ]);
    exit();
}

if ($method === 'post' and !empty($table)) {
    $postData = json_decode(file_get_contents("php://input"), true);
	$name = $postData['username'] ?? "";
	$price = $postData['score'] ?? "";

    if ((!is_numeric($name) AND !empty($name)) AND is_numeric($price)) {
        $insertData = insertData($table, $postData);

        $message = $insertData ? "Data inserted successfully." : "Failed to insert data.";

        response(200, $message);

    } else {
        response(400, "Invalid JSON format");
    }

    exit();
}

if ($method === 'get' and !empty($table) and empty($id)) {

    $allData = getAllData($_GET['table']);


    $allData ? response(200, "Data found", $allData) :
        response(200, "Data Not Found", NULL);

    exit();
}