package com.msa.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class ConfigserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigserverApplication.class, args);
	}

}


/*
http://localhost:9001/user-service/oauth2/callback?
state=uufhLH8DXjLb2W04O3DA7z7N1u2m3HxDwNNKgvE1P8s%3D
&code=4%2F0AWtgzh7uqXbGtyZHeNqj37nXf2TVFwPNl2addTgt5yKUULMW3Zv96zTi1xuRguCSXGXf7g
&scope=email+profile+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile+openid
&authuser=0
&prompt=consent



*/