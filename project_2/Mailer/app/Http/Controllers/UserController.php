<?php 

namespace App\Http\Controllers;

use Auth;
use App\School;
use App\Review;
use App\Level;
use App\Subject;
use App\User;
use App\Login;
use App\Session;
use App\Junction;
use App\Interval;
use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use Illuminate\Hashing\BcryptHashet;

class UsersController extends Controller {
   
	public function register(Request $request) {
		$fields = [
			'email' => 'required',
			'password' => 'required'
		];

		$this->validate($request, $fields);

		if(User::where('email', $request['email'])->first()) {
			return MyResponse::show('Email is already in use', 400);
		}

		$request['password'] = app('hash')->make($request['password']);
		$request['accessToken'] = $this->createToken();

		$user = User::create($request);

		return response()->json(User::find($user->id));
	} 

	public function login(Request $request) {
		$this->validate($request, [
			'email' => 'required',
			'password' => 'required'
		]);

		$user = User::where('email', $request['email'])
					->firstOrFail();

		if(!app('hash')->check($request['password'], $user->password)) {
			return MyResponse::show('Incorrect password', 400);		
		}

		if($user->active == 1) {
			return MyResponse::show('User is already active', 400);
		}

		$accessToken = $this->createToken();

		User::where('id', $user->id)
		    ->update([
				'accessToken' => $accessToken
			]);

		$login = Login::create([
			'user' => $user->id,
			'ip' => $request->ip()
		]);

		return response()->json([
			'accessToken' => $accessToken,
			'login' => $login
		]);	
	}

	private function createToken() {
		do {
			$token = str_random(64);
		}while(User::where("accessToken", $token)->first() instanceof User);
		
		return $token;
	}
}
?>
