<?php

    class STREAMError extends Exception {

        public $code;
        public $message;

        public function __construct($message, $code = 0, Throwable $previous = null) {
            parent::__construct($message, $code, $previous);
        }
    }

?>