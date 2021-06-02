<?php

    require(dirname(__FILE__) . '/SHA1HMACSignature.php');
    require(dirname(__FILE__) . '/STREAMError.php');

    class STREAM {

        private $url;
        private $agent;
        private $signature;
        private $language = "az";
        private $timeout = 30;

        public function setLanguage($language) {
            $this->language = $language;
        }

        public function setTimeout($timeout) {
            $this->timeout = $timeout;
        }

        public function __construct($url, $language = "az") {
            $this->url = $url;
            $this->language = $language;
        }

        public function setAuthenticationData($agent, $key) {
            $this->agent = $agent;
            $this->signature = new SHA1HMACSignature($key);
        }

        public function getCategoryList() {
            if ($this->signature == null) {
                throw new BadFunctionCallException(
                    "Not authenticated. Call 'setAuthenticationData(...)' first"
                    );
            }
            $this->signature->setMethod("GET");
            $this->signature->setPath(
                    sprintf("%s/categories", parse_url($this->url)["path"])
                    );
            $this->signature->clear();

            $curl = curl_init(sprintf("%s/categories", $this->url));
            curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
            curl_setopt($curl, CURLOPT_TIMEOUT, $this->timeout);
	    curl_setopt($curl, CURLOPT_SSL_VERIFYHOST, 0);
	    curl_setopt($curl, CURLOPT_SSL_VERIFYPEER, 0);
            curl_setopt($curl, CURLOPT_HTTPHEADER, array(
                    "X-Agent: " . $this->agent,
                    "X-Signature: " . $this->signature->calculate(),
                    "X-Format: JSON",
                    "X-Language: " . $this->language
            ));
            $data = curl_exec($curl);
            if(curl_errno($curl)) {
                $error = curl_error($curl);
            }
            curl_close($curl);
            if(isset($error)) {
                throw new RuntimeException($error);
            } else {
                $json = json_decode($data);
                if($json->response->code != "0") {
                    throw new STREAMError(
                            $json->response->message,
                            $json->response->code
                            );
                }
                return $json->response->payload;
            }
        }

        public function getServiceList($category, $keyword = null, $offset = 0, $limit = 100) {
            if ($this->signature == null) {
                throw new BadFunctionCallException(
                    "Not authenticated. Call 'setAuthenticationData(...)' first"
                    );
            }
            foreach(array(
                "category" => $category,
                "offset" => $offset,
                "limit" => $limit
                ) as $key => $value) {
                if(!isset($value)) {
                    throw new InvalidArgumentException(
                            "Argument $" . $key . " is missing"
                            );
                }
            }
            $this->signature->setMethod("GET");
            $this->signature->setPath(
                    sprintf("%s/services", parse_url($this->url)["path"])
                    );
            $this->signature->clear();
            $this->signature->addFixedParameter($category);
            $this->signature->addFixedParameter($keyword);
            $this->signature->addFixedParameter($offset);
            $this->signature->addFixedParameter($limit);

            $query = array("category" => $category, "keyword" => $keyword, "offset" => $offset, "limit" => $limit);
            foreach ($query as $key => $value) {
                if(!isset($value) || strlen($value) == 0) {
                    unset($query[$key]);
                }
            }

            $curl = curl_init(sprintf("%s/services?%s", $this->url, http_build_query($query)));
            curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
            curl_setopt($curl, CURLOPT_TIMEOUT, $this->timeout);
	    curl_setopt($curl, CURLOPT_SSL_VERIFYHOST, 0);
	    curl_setopt($curl, CURLOPT_SSL_VERIFYPEER, 0);
            curl_setopt($curl, CURLOPT_HTTPHEADER, array(
                    "X-Agent: " . $this->agent,
                    "X-Signature: " . $this->signature->calculate(),
                    "X-Format: JSON",
                    "X-Language: " . $this->language
            ));
            $data = curl_exec($curl);
            if(curl_errno($curl)) {
                $error = curl_error($curl);
            }
            curl_close($curl);
            if(isset($error)) {
                throw new RuntimeException($error);
            } else {
                $json = json_decode($data);
                if($json->response->code != "0") {
                    throw new STREAMError(
                            $json->response->message,
                            $json->response->code
                            );
                }
                return $json->response->payload;
            }
        }

        public function getService($alias) {
            if ($this->signature == null) {
                throw new BadFunctionCallException(
                    "Not authenticated. Call 'setAuthenticationData(...)' first"
                    );
            }
            foreach(array(
                "alias" => $alias,
                ) as $key => $value) {
                if(!isset($value)) {
                    throw new InvalidArgumentException(
                            "Argument $" . $key . " is missing"
                            );
                }
            }
            $this->signature->setMethod("GET");
            $this->signature->setPath(
                    sprintf("%s/services/%s", parse_url($this->url)["path"], $alias)
                    );
            $this->signature->clear();

            $curl = curl_init(sprintf("%s/services/%s", $this->url, $alias));
            curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
            curl_setopt($curl, CURLOPT_TIMEOUT, $this->timeout);
	    curl_setopt($curl, CURLOPT_SSL_VERIFYHOST, 0);
	    curl_setopt($curl, CURLOPT_SSL_VERIFYPEER, 0);
            curl_setopt($curl, CURLOPT_HTTPHEADER, array(
                    "X-Agent: " . $this->agent,
                    "X-Signature: " . $this->signature->calculate(),
                    "X-Format: JSON",
                    "X-Language: " . $this->language
            ));
            $data = curl_exec($curl);
            if(curl_errno($curl)) {
                $error = curl_error($curl);
            }
            curl_close($curl);
            if(isset($error)) {
                throw new RuntimeException($error);
            } else {
                $json = json_decode($data);
                if($json->response->code != "0") {
                    throw new STREAMError(
                            $json->response->message,
                            $json->response->code
                            );
                }
                return $json->response->payload;
            }
        }

        public function getOptionList($alias, $detailList) {
            if ($this->signature == null) {
                throw new BadFunctionCallException(
                    "Not authenticated. Call 'setAuthenticationData(...)' first"
                    );
            }
            foreach(array(
                "alias" => $alias,
                "detailList" => $detailList
                ) as $key => $value) {
                if(!isset($value)) {
                    throw new InvalidArgumentException(
                            "Argument $" . $key . " is missing"
                            );
                }
            }
            $this->signature->setMethod("GET");
            $this->signature->setPath(
                    sprintf("%s/options", parse_url($this->url)["path"])
                    );
            $this->signature->clear();
            $this->signature->addFixedParameter($alias);

            $query = array("service" => $alias);
            foreach($detailList as $key => $value) {
                $this->signature->addNamedParameter($key, $value);
                $query["(".$key.")"] = $value;
            }
            foreach ($query as $key => $value) {
                if(!isset($value) || strlen($value) == 0) {
                    unset($query[$key]);
                }
            }

            $curl = curl_init(sprintf("%s/options?%s", $this->url, http_build_query($query)));
            curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
            curl_setopt($curl, CURLOPT_TIMEOUT, $this->timeout);
	    curl_setopt($curl, CURLOPT_SSL_VERIFYHOST, 0);
	    curl_setopt($curl, CURLOPT_SSL_VERIFYPEER, 0);
            curl_setopt($curl, CURLOPT_HTTPHEADER, array(
                    "X-Agent: " . $this->agent,
                    "X-Signature: " . $this->signature->calculate(),
                    "X-Format: JSON",
                    "X-Language: " . $this->language
                    )
                    );
            $data = curl_exec($curl);
            if(curl_errno($curl)) {
                $error = curl_error($curl);
            }
            curl_close($curl);
            if(isset($error)) {
                throw new RuntimeException($error);
            } else {
                $json = json_decode($data);
                if($json->response->code != "0") {
                    throw new STREAMError(
                            $json->response->message,
                            $json->response->code
                            );
                }
                return $json->response->payload;
            }
        }

        public function register($alias, $amount, $fee, $currency, $transactionId, $date, $channel, $source, $description, $tag, $detailList) {
            if ($this->signature == null) {
                throw new BadFunctionCallException(
                    "Not authenticated. Call 'setAuthenticationData(...)' first"
                    );
            }
            foreach(array(
                "alias" => $alias,
                "fee" => $fee,
                "currency" => $currency,
                "transactionId" => $transactionId,
                "date" => $date,
                "detailList" => $detailList
                ) as $key => $value) {
                if(!isset($value)) {
                    throw new InvalidArgumentException(
                            "Argument $" . $key . " is missing"
                            );
                }
            }
            $this->signature->setMethod("POST");
            $this->signature->setPath(
                    sprintf("%s/payments", parse_url($this->url)["path"])
                    );
            $this->signature->clear();
            $this->signature->addFixedParameter($alias);
            $this->signature->addFixedParameter($amount);
            $this->signature->addFixedParameter($fee);
            $this->signature->addFixedParameter($currency);
            $this->signature->addFixedParameter($transactionId);
            $this->signature->addFixedParameter($date);
            $this->signature->addFixedParameter($channel);
            $this->signature->addFixedParameter($source);
            $this->signature->addFixedParameter($description);
            $this->signature->addFixedParameter($tag);

            $query = array(
                "service" => $alias,
                "amount" => $amount,
                "fee" => $fee,
                "currency" => $currency,
                "transaction-id" => $transactionId,
                "date" => $date,
                "channel" => $channel,
                "source" => $source,
                "description" => $description,
                "tag" => $tag
                );
            foreach($detailList as $key => $value) {
                $this->signature->addNamedParameter($key, $value);
                $query["(".$key.")"] = $value;
            }
            foreach ($query as $key => $value) {
                if(!isset($value) || strlen($value) == 0) {
                    unset($query[$key]);
                }
            }

            $curl = curl_init(sprintf("%s/payments", $this->url));
            curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
            curl_setopt($curl, CURLOPT_TIMEOUT, $this->timeout);
	    curl_setopt($curl, CURLOPT_SSL_VERIFYHOST, 0);
	    curl_setopt($curl, CURLOPT_SSL_VERIFYPEER, 0);
            curl_setopt($curl, CURLOPT_HTTPHEADER, array(
                    "X-Agent: " . $this->agent,
                    "X-Signature: " . $this->signature->calculate(),
                    "X-Format: JSON",
                    "X-Language: " . $this->language
                    )
                    );
            curl_setopt($curl, CURLOPT_POST, 1);
            curl_setopt($curl, CURLOPT_POSTFIELDS, http_build_query($query));
            $data = curl_exec($curl);
            if(curl_errno($curl)) {
                $error = curl_error($curl);
            }
            curl_close($curl);
            if(isset($error)) {
                throw new RuntimeException($error);
            } else {
                $json = json_decode($data);
                if($json->response->code != "0") {
                    throw new STREAMError(
                            $json->response->message,
                            $json->response->code
                            );
                }
                return $json->response->payload->reference;
            }
        }

        public function pay($reference) {
            if ($this->signature == null) {
                throw new BadFunctionCallException(
                    "Not authenticated. Call 'setAuthenticationData(...)' first"
                    );
            }
            foreach(array(
                "reference" => $reference
                ) as $key => $value) {
                if(!isset($value)) {
                    throw new InvalidArgumentException(
                            "Argument $" . $key . " is missing"
                            );
                }
            }
            $this->signature->setMethod("POST");
            $this->signature->setPath(
                    sprintf("%s/payments/%s", parse_url($this->url)["path"], $reference)
                    );
            $this->signature->clear();

            $curl = curl_init(sprintf("%s/payments/%s", $this->url, $reference));
            curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
            curl_setopt($curl, CURLOPT_TIMEOUT, $this->timeout);
	    curl_setopt($curl, CURLOPT_SSL_VERIFYHOST, 0);
	    curl_setopt($curl, CURLOPT_SSL_VERIFYPEER, 0);
            curl_setopt($curl, CURLOPT_HTTPHEADER, array(
                    "X-Agent: " . $this->agent,
                    "X-Signature: " . $this->signature->calculate(),
                    "X-Format: JSON",
                    "X-Language: " . $this->language
                    )
                    );
            curl_setopt($curl, CURLOPT_POST, 1);
            $data = curl_exec($curl);
            if(curl_errno($curl)) {
                $error = curl_error($curl);
            }
            curl_close($curl);
            if(isset($error)) {
                throw new RuntimeException($error);
            } else {
                $json = json_decode($data);
                if($json->response->code != "0") {
                    throw new STREAMError(
                            $json->response->message,
                            $json->response->code
                            );
                }
                return $json->response->payload;
            }
        }

        public function getPayment($reference) {
            if ($this->signature == null) {
                throw new BadFunctionCallException(
                    "Not authenticated. Call 'setAuthenticationData(...)' first"
                    );
            }
            foreach(array(
                "reference" => $reference
                ) as $key => $value) {
                if(!isset($value)) {
                    throw new InvalidArgumentException(
                            "Argument $" . $key . " is missing"
                            );
                }
            }
            $this->signature->setMethod("GET");
            $this->signature->setPath(
                    sprintf("%s/payments/%s", parse_url($this->url)["path"], $reference)
                    );
            $this->signature->clear();

            $curl = curl_init(sprintf("%s/payments/%s", $this->url, $reference));
            curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
            curl_setopt($curl, CURLOPT_TIMEOUT, $this->timeout);
	    curl_setopt($curl, CURLOPT_SSL_VERIFYHOST, 0);
	    curl_setopt($curl, CURLOPT_SSL_VERIFYPEER, 0);
            curl_setopt($curl, CURLOPT_HTTPHEADER, array(
                    "X-Agent: " . $this->agent,
                    "X-Signature: " . $this->signature->calculate(),
                    "X-Format: JSON",
                    "X-Language: " . $this->language
            ));
            $data = curl_exec($curl);
            if(curl_errno($curl)) {
                $error = curl_error($curl);
            }
            curl_close($curl);
            if(isset($error)) {
                throw new RuntimeException($error);
            } else {
                $json = json_decode($data);
                if($json->response->code != "0") {
                    throw new STREAMError(
                            $json->response->message,
                            $json->response->code
                            );
                }
                return $json->response->payload;
            }
        }

        public function getPaymentList($from, $to, $tag = null, $offset = 0, $limit = 100) {
            if ($this->signature == null) {
                throw new BadFunctionCallException(
                    "Not authenticated. Call 'setAuthenticationData(...)' first"
                    );
            }
            foreach(array(
                "from" => $from,
                "to" => $to
                ) as $key => $value) {
                if(!isset($value)) {
                    throw new InvalidArgumentException(
                            "Argument $" . $key . " is missing"
                            );
                }
            }
            $this->signature->setMethod("GET");
            $this->signature->setPath(
                    sprintf("%s/payments", parse_url($this->url)["path"])
                    );
            $this->signature->clear();
            $this->signature->addFixedParameter($tag);
            $this->signature->addFixedParameter($from);
            $this->signature->addFixedParameter($to);
            $this->signature->addFixedParameter($offset);
            $this->signature->addFixedParameter($limit);

            $query = array("tag" => $tag, "from" => $from, "to" => $to, "offset" => $offset, "limit" => $limit);
            foreach ($query as $key => $value) {
                if(!isset($value) || strlen($value) == 0) {
                    unset($query[$key]);
                }
            }
            var_dump($query);

            $curl = curl_init(sprintf("%s/payments?%s", $this->url, http_build_query($query)));
            curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
            curl_setopt($curl, CURLOPT_TIMEOUT, $this->timeout);
	    curl_setopt($curl, CURLOPT_SSL_VERIFYHOST, 0);
	    curl_setopt($curl, CURLOPT_SSL_VERIFYPEER, 0);
            curl_setopt($curl, CURLOPT_HTTPHEADER, array(
                    "X-Agent: " . $this->agent,
                    "X-Signature: " . $this->signature->calculate(),
                    "X-Format: JSON",
                    "X-Language: " . $this->language
            ));
            $data = curl_exec($curl);
            if(curl_errno($curl)) {
                $error = curl_error($curl);
            }
            curl_close($curl);
            if(isset($error)) {
                throw new RuntimeException($error);
            } else {
                $json = json_decode($data);
                if($json->response->code != "0") {
                    throw new STREAMError(
                            $json->response->message,
                            $json->response->code
                            );
                }
                return $json->response->payload;
            }
        }
    }

?>