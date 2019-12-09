<?php

$app->get('/', function($app) {
    return $app->app->version();
});

$app->group(['prefix' => 'api/v1'], function($app) {
	$app->post('register', 'UsersController@register');	
	$app->post('login', 'UsersController@login');
	
	$app->group(['middleware' => 'auth'], function($app) {
		$app->post('mailer', 'MailController@sendMail');
	});
});
?>
