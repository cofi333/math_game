<?php
require_once '../config/config.php';
$GLOBALS['pdo'] = connectDatabase($dsn, $pdoOptions);