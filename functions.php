<?php
require_once 'config.php';
$GLOBALS['pdo'] = connectDatabase($dsn, $pdoOptions);

//getAllRecordsFromDB
function getAllData(string $table): array|bool
{
	$sql = "SELECT * FROM $table ORDER BY CAST(score AS SIGNED) DESC";
    $stmt = $GLOBALS['pdo']->prepare($sql);
    $stmt->execute();

    return $stmt->fetchAll(PDO::FETCH_ASSOC);
}

//insertNewRecordInDB
function insertData(string $table, array $postData): bool
{
    $sql = "INSERT INTO $table (username, score) VALUES (:username, :score)";
    $stmt = $GLOBALS['pdo']->prepare($sql);
    $stmt->bindValue(':username', $postData['username'], PDO::PARAM_STR);
    $stmt->bindValue(':score', $postData['score'], PDO::PARAM_STR);
    return $stmt->execute();
}

//apiCallResponse
function response(int $status, string $status_message, mixed $data = null): void
{
    header("HTTP/1.1 " . $status);

    $response['status'] = $status;
    $response['status_message'] = $status_message;

    if ($data !== null) {
        $response['data'] = $data;
    }

    echo json_encode($response);
}