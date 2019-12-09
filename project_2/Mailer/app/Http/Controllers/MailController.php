<?php

namespace App\Http\Controllers;

use App\Mail\DefaultMail;

class MailController extends Controller {
	
	public function sendMail(Request $request) {
		Mail::to('example@gmail.com')
			->send(new DefaultMail('John Doe'));
	}
}
