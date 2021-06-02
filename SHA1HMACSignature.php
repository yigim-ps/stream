<?php

    class SHA1HMACSignature {

        private $key;
        private $method = "GET";
        private $path = "/";
        private $fixed = array();
        private $named = array();

        public function setMethod($method) {
            $this->method = $method;
        }

        public function setPath($path) {
            $this->path = $path;
        }

        public function addFixedParameter($value) {
            if(!isset($value) || strlen($value) == 0) {
                return;
            }
            $this->fixed[] = $value;
        }

        public function addNamedParameter($name, $value) {
            if(!isset($value) || strlen($value) == 0) {
                return;
            }
            $this->named[$name] = $value;
        }

        public function clear() {
            $this->fixed = array();
            $this->named = array();
        }

        public function __construct($key) {
            $this->key = $key;
        }

        public function calculate($time = null) {
            if ($time == null) {
                $time = round(microtime(true) * 1000) / 200;
            }
            ksort($this->named);
            $tmp = array();
            foreach($this->fixed as $value) {
                $tmp[] = sprintf("%d%s", strlen($value), $value);
            }
            foreach($this->named as $key => $value) {
                $tmp[] = sprintf("%d%s", strlen($value), $value);
            }
            $data = sprintf(
                    "%d%s%d%s%s%d",
                    strlen($this->method),
                    $this->method,
                    strlen($this->path),
                    $this->path,
                    implode($tmp),
                    $time
                    );            
            return base64_encode(hash_hmac(
                    "sha1",
                    $data,
                    $this->key,
                    true
                    )
                    );
        }
    }

?>