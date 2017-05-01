<?php

require 'vendor/autoload.php';
require_once 'repository/MessageRepository.php';
require_once 'repository/UserRepository.php';
require_once 'repository/ForumRepository.php';
require_once 'repository/TopicRepository.php';
require_once 'repository/PostRepository.php';

$app = new Slim\App();

// Define app routes of the application. All responses are in json format

// get messages for user and create the json response
$app->get('/messages/user/{userId}', function ($request, $response, $args) {
    $messageRepository = new MessageRepository();
    $result = $messageRepository->getMessagesByUserId($args['userId']);

    $response = array();
    $response['message'] = $result;
    return json_encode($response);
});

// create new message
$app->post('/message', function() use ($app) {

    // reading post params
    $message = $_REQUEST["message"];
    $userId = $_REQUEST["userId"];
    $messageRepository = new MessageRepository();
    $result = $messageRepository->createMessage($message, $userId);

    $response = array();
    $response['message'] = $result;
    return json_encode($response);
});

// assign message to multiple recipients
$app->post('/messages', function() use ($app) {

    // reading post params
    $message = $_REQUEST["message"];
    $userId = $_REQUEST["userId"];

    $recipientIdsStr = $_REQUEST["recipientIds"];
    $recipientIds = array_map("intval", explode(",", $recipientIdsStr));
    $messageRepository = new MessageRepository();
    $result = $messageRepository->createMessage($message, $userId);
    $messageId = $messageRepository->getMessageByTextAndUser($message, $userId);
    foreach ($recipientIds as $r) {
        $messageRepository->assignMessageToRecipient($messageId['id'], $r);
    }
    $response = array();
    $response['message'] = $result;
    return json_encode($response);
});

$app->post('/messages/broadcast', function() use ($app) {

    // reading post params
    $message = $_REQUEST["message"];
    $userId = $_REQUEST["userId"];

    $userRepository = new UserRepository();
    $recipientIds = $userRepository -> getUsersApartCurrent($userId);

    $messageRepository = new MessageRepository();
    $result = $messageRepository->createMessage($message, $userId);
    $message = $messageRepository->getMessageByTextAndUser($message, $userId);

    foreach ($recipientIds as $r) {
        $messageRepository->assignMessageToRecipient($message['id'], $r['id']);
    }
    $response = array();
    $response['message'] = $result;
    return json_encode($response);

});

// get user history
$app->get('/user/{userId}/history', function ($request, $response, $args) {
    $userRepository = new UserRepository();

    //get the messages that the user sent and received
    $result = $userRepository->getHistoryForUser($args['userId']);

    $response = array();
    $response['history'] = $result;
    return json_encode($response);
});

// get all forums
$app->get('/forums', function ($request, $response, $args) {
    $forumRepository = new ForumRepository();

    //get all forums
    $result = $forumRepository->getForums();

    $response = array();
    $response['forums'] = $result;
    return json_encode($response);
});


/**************************** Topics *****************/
// get all topics for a forum
$app->get('/topics/forumId/{forumId}', function ($request, $response, $args) {
    $topicRepository = new TopicRepository();
    $result = $topicRepository->getTopicsForForum($args['forumId']);


    $response = array();
    $response['topics'] = $result;
    return json_encode($response);
});

// create new topic
$app->post('/topics', function() use ($app) {

    // reading post params
    $title = $_REQUEST["title"];
    $text = $_REQUEST["text"];
    $forumId = $_REQUEST["forumId"];
    $userId = $_REQUEST["userId"];
    $topicRepository = new TopicRepository();
    $result = $topicRepository->addTopic($title, $text, $userId, $forumId);

    $response = array();
    $response['message'] = $result;
    return json_encode($response);
});

/**************************** Posts *****************/
// get posts for topic
$app->get('/posts/topicId/{topicId}', function ($request, $response, $args) {
    $postRepository = new PostRepository();
    $result = $postRepository->getPostsForTopic($args['topicId']);


    $response = array();
    $response['posts'] = $result;
    return json_encode($response);
});

// create new post
$app->post('/posts', function() use ($app) {

    // reading post params
    $text = $_REQUEST["text"];
    $topicId = $_REQUEST["topicId"];
    $userId = $_REQUEST["userId"];
    $postRepository = new PostRepository();
    $result = $postRepository->addPost($text, $userId, $topicId);

    $response = array();
    $response['message'] = $result;
    return json_encode($response);
});

/*************************** Login User *********************/
// service for logging the user in the application
$app->get('/user/login', function ($request, $response, $args) {

    $username = $_REQUEST["username"];
    $password = $_REQUEST["password"];

    $userRepository = new UserRepository();
    $result = $userRepository->authenticateUser($username, $password);
    $response = array();

    if(!$result){
        $response['userId'] = -1;
    } else{
        $response['userId'] = $result['id'];
    }

    return json_encode($response);
});


/***************** History *****************/
// create a new location for a user
$app->post('/user/history', function() use ($app) {

    // reading post params
    $latitude = $_REQUEST["latitude"];
    $longitude = $_REQUEST["longitude"];
    $userId = $_REQUEST["userId"];

    $userRepository = new UserRepository();
    $result = $userRepository->addLocationForUser($userId, $longitude, $latitude);

    $response = array();
    $response['message'] = $result;
    return json_encode($response);
});

// get the location of a user
$app->get('/user/location', function ($request, $response, $args) {
    $userRepository = new UserRepository();
    $result = $userRepository->getUserRecentLocations();

    $response = array();
    $response['users'] = $result;
    return json_encode($response);
});

// get user details
$app->get('/user/details/{username}', function ($request, $response, $args) {
    $userRepository = new UserRepository();
    $result = $userRepository->getUserDetails($args['username']);

    $response = array();
    $response['details'] = $result;
    return json_encode($response);
});

/***    Register User ****/
$app->post('/user/register', function() use ($app) {

    // reading post params
    $username = $_REQUEST["username"];
    $password = $_REQUEST["password"];
    $occupation = $_REQUEST["occupation"];
    $birth_date = $_REQUEST["birthDate"];
    $hobbies = $_REQUEST["hobbies"];
    $interests = $_REQUEST["interests"];


    $userRepository = new UserRepository();
    $result = $userRepository->addUser($username, $password, $occupation, $birth_date, $hobbies, $interests);

    $response = array();
    $response['message'] = $result;
    return json_encode($response);
});


// Run app
$app->run();
