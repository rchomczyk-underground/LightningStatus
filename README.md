# rose-uptimer
Simple and lightweight application which is checking status of your web services and send a notification if it is down.

## Example configuration
```json
{
  "watched-objects": [
    {
      "name": "rosesapphire's website",
      "address": "https://rosesapphire.pl",
      "http-method": "GET",
      "http-minimum-accepted-code": 200,
      "http-maximum-accepted-code": 299,
      "http-headers": {
        
      }
    },
    {
      "name": "rosesapphire's storehouse",
      "address": "https://storehouse.rosesapphire.pl",
      "http-method": "GET",
      "http-minimum-accepted-code": 200,
      "http-maximum-accepted-code": 299,
      "http-headers": {
        
      }
    }
  ],
  "delay": 15,
  "webhook-uri": "You should put that value on your own."
}
```

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## Licensing
[rose-uptimer](https://github.com/RoseSapphire/rose-uptimer) plugin is released under [Apache License 2.0](./LICENSE) conditions.