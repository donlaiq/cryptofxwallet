# Crypto FX Wallet

What is Crypto FX Wallet?
-------------------------

Crypto FX Wallet is a layer working above the command line wallet of a crypto node.
It should work on Bitcoin and all the cryptocoins derivated from it, like Bitcoin Cash,
ZCash, ZenCash, etc.


Why Crypto FX Wallet?
---------------------

Crypto FX Wallet started as a personal project to see my BitcoinZ wallet using a GUI.
Previous to the fork in June 2018, there was a graphical Java Swing wallet based on a ZenCash project,
but after the fork it didn't work anymore for me. Of course, I could do the usual operations through
the command line, but I wanted something faster and less prone to errors. 
The project is based on JavaFX 11 and allows to change some features (explained below) avoiding the 
process of rebuilding everything.


What do I need to run Crypto FX Wallet?
------------------------------------

### A proper configuration to run a crypto node
This is basically a layer over the command line of a crypto node, so you should follow 
the official instructions to set up your desired node.
https://github.com/bitcoin/bitcoin (for Bitcoin Core),
https://github.com/btcz/bitcoinz (for BitcoinZ),
etc.

### Java 11
Download and install the last Java SE Development Kit 11 for your OS.
https://www.oracle.com/technetwork/java/javase/downloads/index.html

### JavaFX 11
Download JavaFX 11 for your OS and unzip it in a known folder.
https://gluonhq.com/products/javafx/

### A proper configuration of the 'wallet.properties' file
You can follow the examples uploaded in the Release tab, but here you can get a glimpse
about what every line means:

node.path: absolute path the executables of the node, usually inside the 'src' folder.
javafx.sdk.path: <folder_where_you_unzip_javafx>/lib.
blockchain.explorer.blockcount.api: URL to some REST service providing the total amount of blocks
for the crypto coin.
time.zone: to use the same time that the blockchain uses to watch the transactions.
coin.code: the code of the cryptocoin. ZEC for ZCash, LTC for Litecoin, etc.
start.command: executable file inside node.path to start the node.
cli.command: executable file inside node.path to query to the blockchain.
system.command: system interpreter. 'sh' on linux distros, 'cmd.exe' in Windows.
system.command.parameter: parameter going with the system interpreter. '-c' in linux distros,
'/k' in Windows.
encode: to get a proper string configuration according to the desired foreign language.
foreign.language: the default language is English. If you want to replace the second language, let's 
say to German, then you should put 'German' here.
two.kind.of.addresses: true if there are Z and T addresses; false, otherwise.
donate.address: if you are moving to another crypto coin, let's say X Coin, and you want to put one of 
my X Coin addresses here.


### A proper configuration of the 'command.properties' file
You should be able to replace the BitcoinZ commands for the ones of the other crypto coin.
They are very similar, but they are not the same. 



How should I run Crypto FX Wallet?
----------------------------------

You can use the package for your OS in the tab Releases or build the jar file from the source code
and run them with the corresponding parameters to call the JavaFX libraries, but I advise you to use 
the scripts to make it easier.
I can't test it in Mac OS, but it should be able to run it too.



How should I construct Crypto FX Wallet from the source code?
-------------------------------------------------------------

Maybe it is not the best advice, but it was the easier way to me. The code was managed from an Eclipse IDE,
so I have created the jar files doing the following from Eclipse:
File->Export...->Java->Runnable JAR file->Extract required libraries into generated JAR->Finish.
It is a Maven project, but I have used Maven just to manage the dependencies. I didn't find a way to 
set up a convincing building task. Maybe in the next release.



How should I use Crypto FX Wallet?
----------------------------------

After the application is started you can see in the top of the window a bar menu
with options to backup or import the addresses of your wallet. The backup will save 
a file called "cryptoBackup.txt" in the folder you choose. To import an addresses
you need to choose a file from your file system (it doesn't matter its name or its
extension), but the application will expect that every line starts with 'z' or 't'.
Finally, what it matters to import an address is to have its private key, but the 
methods to import one or another are different, so it's useful to know that when
there are two kind of addresses and use the right method.

t-<private_key> 
z-<private_key> 
zzzzzasddss-<private_key> 
t*/3/\4re-<private_key> 

If the wallet has just transparent addresses, then t-<private_key> will work fine too.

Below the bar menu, in the left side, there are three panels.
The top panel shows the balance of the wallet.
The middle panel shows the synchronization of the node respect to a full synchronized node.
The bottom panel shows the balance of the addresses of your wallet and allows to send coins
to another addresses.
To send money you should choose an Address from the table 'My addresses', set a destination 
address in the corresponding text field, put the amount to send in the corresponding 
text field and click on the send button.
Donate button is just a stupidity showing an animation, but its only utility is to change
the destination address to one of my addresses. Next you should click on the send button
if you really want to donate.


Flexibility of Crypto FX Wallet
-------------------------------

If you are not a coder you can do several changes to the application without rebuilding the whole thing.

### The video of the splash screen is boring

You can change it for one of your election, but remember to call it 'splashScreenVideo.mp4' and to put it
inside the folder 'resources'.

### I would like to change some colors, fonts, etc.

You can edit the files 'style.css' and 'messagePopupStyle.css' inside the 'resources' folder to do it. 
I suggest you to keep an eye on the .fxml files in the source code to get a better understanding about what 
you are changing.

### I would like to use another English sentences in the application

Yoo should edit the 'english.properties' file inside the 'resource' folder.

### I don't want Spanish as a second language

You can edit the 'foreign.properties' file with your own language.

### I don't want to have the logo from BitcoinZ

Change the images 'background.png' and 'mainLogo.png' for your desired replacement. Remember to keep the same 
names and keep in mind that 'mainLogo.png' is hide when the synchronization is not 100%. So play with that.

### I would like use it with another crypto coin

You can do it modifying the files 'wallet.properties' and 'command.properties' as it's explained above.

### I would like to paint the arrow of pink

Ok, do it.


License
-------

Bitcoin Core is released under the terms of the MIT license. See [COPYING](COPYING) for more
information or see https://opensource.org/licenses/MIT.

Donate
------

### Buy me a building, an helicopter, a yacht, etc.

BTC: 
ETH:
BCH:
LTC:

### Pay some of my vices like alcohol, cigarettes, casino, etc.

BTCZ:
XMR:
IOTA:
PAY:
ETC:
BTG:
OMG:
QTUM:
BTM:
STEEM:

### Think about the hungry children (my hungry children)

ZEC:
USDT:
TUSD:


FAQ
---

### The update of Crypto FX Wallet is too slow

The wallet will look for updates every minute. If you do an operation that should be super fast
(to create a new address, for example), hopefully it will be visible in the next updating. 

### Every kind of transfer from my Z address to another address set the balance of my Z address almost to zero

Don be panic! In a few minutes it will be normalized again. It's a weird behavior, at least of BitcoinZ,
that I don't know why is happening.

### Is Crypto FX Wallet safe?

I guess it should be as safe as your command line wallet, but I'm not a security guru, so I can't confirm that.
Use it at your own risk.

### I want to pay you with another crypto coin, not listed before, what should I do?

Send me a direct message through twitter to @donlaiq.

### I want to hire you, how can I contact you?

Ok, then send me a direct message through twitter to @donlaiq.

### I want to follow you in twitter. Is @donlaiq your twitter?

Why would you do that? There's nothing interesting to follow.

### Your English sucks. Do you think to work on it?

Yes, definitely.

### Your application sucks too.

Well... you'll get a proper discount with your next donation.
