
package com.henrique.nicolli.test;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.event.S3EventNotification;

/**
 * AWS Lambda function with S3 trigger.
 * 
 */
public class App implements RequestHandler<S3EventNotification, String> {
	
	static final Logger log = LoggerFactory.getLogger(App.class);
	

	@Override
	public String handleRequest(S3EventNotification s3EventNotification, Context context) {
		log.info("Lambda function is invoked:" + s3EventNotification.toJson());
		
		
		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
		DynamoDB dynamoDB = new DynamoDB(client);

		Table table = dynamoDB.getTable("file_data");
		
		Item item = new Item()
				.withPrimaryKey("id",new Random().nextInt())
			    .withString("log", s3EventNotification.toJson().toString());
		
		PutItemOutcome outcome = table.putItem(item);
		

		return null;
	}

}
