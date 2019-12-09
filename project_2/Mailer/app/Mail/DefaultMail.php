<?php

namespace App\Mail;

use Illuminate\Bus\Queueable;
use Illuminate\Mail\Mailable;
use Illuminate\Queue\SerializesModels;
use Illuminate\Contracts\Queue\ShouldQueue;
use App\User;

class DefaultMail extends Mailable {

    use Queueable, SerializesModels;

    public $name;

    public function __construct(User $name) {
        $this->name = $name;
    }

	public function build() {
        return $this->view('mails.default');
    }
}
