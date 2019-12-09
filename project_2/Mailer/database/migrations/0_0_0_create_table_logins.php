<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateTableLogins extends Migration {
    
	public function up() {
        Schema::create('logins', function(Blueprint $table) {
            $table->increments('id');
         	$table->string('ip', 15);
			$table->integer('user')->unsigned();
			$table->foreign('user')->references('id')->on('users');
			$table->timestamps();
        });
	}

    public function down() {
        Schema::drop('logins');
    }
}
