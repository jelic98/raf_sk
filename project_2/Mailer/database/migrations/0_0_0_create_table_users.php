<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateTableUsers extends Migration {

    public function up() {
        Schema::create('users', function(Blueprint $table) {
            $table->increments('id');
			$table->string('email')->unique();
			$table->string('password', 60);
			$table->string('accessToken', 64)->unique();
			$table->timestamps();
        });
    }

    public function down() {
        Schema::drop('users');
    }
}
