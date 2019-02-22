# Crypto FX Wallet

Video tutorial
-------------------------
https://www.youtube.com/watch?v=3amZLwUk7C4

![](/main_crypto_fx_wallet.png)

<br/><br/>
What is Crypto FX Wallet?
-------------------------

Crypto FX Wallet is a layer working above the command line wallet of a crypto node. It handles just a limited subset of all the available commands.<br/>
It should work on Bitcoin and all the cryptocoins derivated from it, like Bitcoin Cash, ZCash, Bitcoin Gold, etc.

<br/><br/>
Why Crypto FX Wallet?
---------------------

Crypto FX Wallet started as a personal project to see my BitcoinZ wallet using a GUI.<br/>
Previous to the fork in June 2018, there was a graphical Java Swing wallet based on a ZenCash project,
but after the fork it didn't work anymore for me. Of course, I could do the usual operations through
the command line, but I wanted something faster and less prone to errors.<br/>
The project is based on JavaFX 11  and the Eclipse IDE and allows to change some features (explained below) avoiding the 
process of rebuilding everything.<br/>

<br/><br/>
What do I need to run Crypto FX Wallet?
------------------------------------

### A proper configuration to run a crypto node
This is basically a layer over the command line of a crypto node, so you should follow the official instructions to set up your desired node.<br/>
https://github.com/bitcoin/bitcoin (for Bitcoin Core),<br/>
https://github.com/btcz/bitcoinz (for BitcoinZ),<br/>
etc.<br/>

### Java 11
Download and install the last Java SE Development Kit 11 for your OS.<br/>
https://www.oracle.com/technetwork/java/javase/downloads/index.html<br/>

### JavaFX 11
Download JavaFX 11 for your OS and unzip it in a known folder.<br/>
https://gluonhq.com/products/javafx/<br/>

### A proper configuration of the 'setup.properties' file
The current version of the application allows to configure this file directly from the GUI. There are default options for every single cryptocoin configured in the wallet, you can modify at your will. <br/>

<br/><br/>
How should I run Crypto FX Wallet?
----------------------------------

You can use the package for your OS in the tab Releases or build the jar file from the source code
and run them with the corresponding parameters to call the JavaFX libraries, but I advise you to use 
the scripts to make it easier.<br/>
I couldn't test it in Mac OS, but it should be able to run it too.<br/>


<br/><br/>
How should I construct Crypto FX Wallet from the source code?
-------------------------------------------------------------

Maybe it is not the best advice, but it was the easier way to me. The code was managed from an Eclipse IDE,
so I have created the jar files doing the following from Eclipse:<br/>
File->Export...->Java->Runnable JAR file->Extract required libraries into generated JAR->Finish.<br/>
It is a Maven project, but I have used Maven just to manage the dependencies. I didn't find a way to 
set up a convincing building task. Maybe in the next release.<br/>
If you want to run the jar file directly, remember to run it with the required parameters to <javafx_sdk_folder>/lib.
That is what the included scripts are for.


<br/><br/>
How should I use Crypto FX Wallet?
----------------------------------

After the application is started you can see in the top of the window a bar menu with options to backup or import the addresses of your wallet. The backup will save a file called "cryptoBackup.txt" in the folder you choose. To import an addresses
you need to choose a file with an UTF-8 encode from your file system (it doesn't matter its name or its extension), but the application will expect to find the character '-' in every line. If the left side of the character '-' starts with a z, then the address to import is a Z address. If the left side of the character '-' starts with anything else, then it tries to import the transparent address handled by the wallet. The right side of the character '-' should be the private key of the address. <br/>
Next I show you a few valid lines you can set in the file, replacing <private_key> by your own private key:<br/><br/>

-<private_key> (for T address)<br/>
z-<private_key> (for Z address)<br/>
zzzzzasddss-<private_key> (for Z address)<br/>
3*/3/\4re-<private_key> (for T address)<br/><br/>

Below the bar menu, in the left side, there are three panels.<br/>
The top panel shows the balance of the wallet.<br/>
The middle panel shows the synchronization of the node respect to a full synchronized node.<br/>
The bottom panel shows the balance of the addresses of your wallet and allows to send coins
to another addresses.<br/>
To send money you should choose an Address from the table 'My addresses', set a destination 
address in the corresponding text field, put the amount to send in the corresponding 
text field and click on the send button.<br/>
Donate button is just a stupidity showing an animation, but its only utility is to change
the destination address to one of my addresses. Next you should click on the send button
if you really want to donate.<br/><br/>

The right side of the application shows the last 100 transactions associated with addresses of the wallet.

<br/><br/>
Flexibility of Crypto FX Wallet
-------------------------------

If you are not a coder you can do several changes to the application without rebuilding the whole thing.<br/>

### The video of the splash screen is boring

You can change it for one of your election, but remember to call it **splashScreenVideo.mp4** and to put it
inside the folder **resources**.<br/>

### I would like to change some colors, fonts, etc.

You can edit the files **style.css** and **messagePopupStyle.css** inside the **resources** folder to do it. 
I suggest you to keep an eye on the **.fxml files** in the source code to get a better understanding about what 
you are changing.<br/>

### I would like to use another English sentences in the application

Yoo should edit the **english.properties** file inside the **resource** folder.<br/>

### I don't want Spanish as a second language

You can edit the **foreign.properties** file with your own language.<br/>

### I don't want to have the logo from BitcoinZ inside the application

Change the images **background.png** and **mainLogo.png** for your desired images. Remember to keep the same 
names and keep in mind that **mainLogo.png** is hide when the synchronization is not of 100%. So play with that.<br/>

### I would like use it with another crypto coin

You can do it modifying the files **wallet.properties** and **command.properties** as it's explained above.<br/>

### Remember not to change the left side of the '=' in the .properties files, just the right side

The application is expecting to find those name.<br/>

### I would like to paint the arrow of pink

Ok, do it.<br/>

<br/><br/>
Donate
------

### Buy me a building, an helicopter, a yacht, etc.

**BTC**: 3PcmYiCNsFUJiqwqNcJ78RAk9zhC7b3t2m<br/>
**ETH**: 0xe897cF267CE2bad3C571940F5a1176Ddcf0819f7<br/>
Any token based on **ETH** like **OMG**, **USDT**, **TUSD**, **BTM**, **PAY**, etc.: 0xe897cF267CE2bad3C571940F5a1176Ddcf0819f7<br/><br/>

### Pay some of my vices like alcohol, cigarettes, casino, etc.

**BTCZ**: t1NmxPMWY8EPELxAKRnHi9NGW9NWNypzLzo<br/>
**XMR**: 45jzGRAM3pAJ19fdtVagrTUzwfpL6EbKo7bvK8jybRJaQcpQYXd4pWE7DKurUvq7pe7w2TbnEatuWAw7ArfRu1Jd33oBWTb<br/>
**IOTA**: T9LERQPWWYOUFPJKQPRSWUTJWOCG9TLPMOROFNZV9IGYQEPINZPGUFFGYFPREMWFWATZNTQFNYMEYMMEAUDECTMZMW<br/>
**ETC**: 0x009e9791b8da25bBda05aD0b92f83615a623D544<br/>
**BTG**: GNDeARn5SG9EpZKCWqWT1FAyp8DjtTRtyx<br/>
**QTUM**: QRoxfYMNadqeQmtNWhajVjX5JgRgZczaC9<br/>
**DOGE**: DDKL7UuK6jEATb87BMxRP5bWJi18soqKD3<br/>
**XLM**: GA2BI5XZ6DXITCF36TSPFTOACUONI2524Z4IF2CGWR7CRIPN6MRBC7ZT<br/><br/>

### Think about the hungry children (my hungry children)

**ZEC**: t1ZzxhxiXcUWsaSCTUzKxBNepq5ScBQsFLK<br/>
**BCH**: 1Lw5m8KVU9ww9mpK92mvAzgu1xXhygDQLp<br/>
**LTC**: LNAGuJFRSaahaGYi7cGAbYfdANtwAw6BcS<br/><br/>

<br/><br/>
FAQ
---

### I'm getting this error 'Error: JavaFX runtime components are missing, and are required to run this application' when I try to run the application from my IDE or running directly the executable jar file.

Probably, you have forgotten (or mistyped) to set the parameters to the absolute JavaFX SDK 11 path.<br/>
If you are trying to run the jar file directly, then you should put the following in between **java -jar** and **cryptoFXWallet.jar**.<br/>
If you are trying to run it from your console, then you should add the following in the VM arguments options (**Run as...->Run Configurations...->Arguments->VM Arguments** for the Eclipse IDE):<br/><br/>

--module-path=<javafx_sdk_folder>/lib --add-modules=javafx.controls --add-modules=javafx.fxml --add-modules=javafx.media

<br/>
That's why run it using the script is easier once you set the required absolute paths there.

### The update of Crypto FX Wallet is too slow

The wallet will look for updates every minute. If you do an operation that should be super fast
(to create a new address, for example), hopefully it will be visible in the next updating.<br/>

### Every kind of transfer from my Z address to another address set the balance of my Z address almost to zero

Don be panic! In a few minutes it will be normalized again. It's a weird behavior, at least of BitcoinZ,
that I don't know why is happening.<br/>

### There are some things I can't do with Crypto FX Wallet

Crypto FX Wallet is just a subset of the command line wallet, handling the more relevant commands to me.

### Is Crypto FX Wallet safe?

I guess it should be as safe as your command line wallet, but I'm not a security expert, so I can't confirm that.
Use it at your own risk.<br/>

### I want to pay you with another crypto coin, not listed before, what should I do?

Let me know it and I'll be glad to create a new wallet for that cryptocoin.<br/>

### I want to hire you, how can I contact you?

Ok, then send me a direct message through twitter to @donlaiq.<br/>

### I want to follow you in twitter. Is @donlaiq your twitter?

Why would you do that? There's nothing interesting to follow.<br/>

### Your English sucks. Do you think to work on it?

Yes, definitely.<br/>

### Your application sucks too.

Well... you'll get a proper discount with your next donation.<br/>

<br/><br/>
License
-------
Crypto FX Wallet is released under the terms of the MIT license. See https://opensource.org/licenses/MIT.<br/>
