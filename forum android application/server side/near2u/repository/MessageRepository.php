<?php

require_once(dirname(__FILE__).'/../helpers/DbConnect.php');

class MessageRepository {

    private $conn;

    function __construct() {

        // opening db connection
        $db = new DbConnect();
        $this->conn = $db->connect();
    }

    /**
     * get messages for user id
     */
    function getMessagesByUserId($userId) {

        $stmt = $this->conn->prepare("SELECT * FROM n2u_message WHERE from_user_id = ?");
        $stmt->bind_param("i", $userId);
        $stmt->execute();
        $messages = $stmt->get_result()->fetch_assoc();
        $stmt->close();
        return $messages;

    }

    /**
     * create a new message
     */
    function createMessage($message, $userId) {

        $stmt = $this->conn->prepare("INSERT INTO n2u_message(message, from_user_id, date_sent) values(?, ?,NOW())");
        $stmt->bind_param("si",$message, $userId);

        $stmt->execute();

        $stmt->close();
        return "Success ";
    }


    function assignMessageToRecipient($messageId, $recipientId) {

        $stmt = $this->conn->prepare("INSERT INTO n2u_message_to_receivers(message_id, receiver_id) values(?, ?)");
        $stmt->bind_param("ii",$messageId, $recipientId);

        $stmt->execute();

        $stmt->close();
        return "Success ";
    }

    function getMessageByTextAndUser($message, $userId) {
        $stmt = $this->conn->prepare("SELECT * FROM n2u_message WHERE from_user_id = ? and message =? ");
        $stmt->bind_param("is", $userId, $message);
        $stmt->execute();
        $message = $stmt->get_result()->fetch_assoc();
        $stmt->close();
        return $message;
    }

}