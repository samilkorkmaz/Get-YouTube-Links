# GetYoutTubeLinks
Get list of links to all your uploaded videos on YouTube.

I upload my videos to YouTube with the privacy setting of "Unlisted" which means that only people who have the link to a video can watch it. I need to share those links with a small number of people like my wife and parents. Currently, there is no way to do that on YouTube. Sharing links manually is tedious because I have over 800 uploads. I wanted a an automated way to go to my YouTube page, extract the links of all my uploads so that I can copy those links and send a single email to the people with whom I want to share my videos.

<b>Steps</b>:
* Open Chrome, sign in to your Google account if you haven't already.
* Navigate to uploaded video list page (https://www.youtube.com/my_videos?o=U&pi=1).
* Run the Macro Scheduler 14 <a href="https://github.com/samilkorkmaz/Get-YouTube-Links/blob/master/getYouTubeLinks.scp">script</a> which opens and saves all video list pages as html files to local disk. You can download a <a href="https://www.mjtnet.com/downloads.htm">30 day trial</a> of Macro Scheduler to run this script.
* Go to the directory where the java program is and create a directory with the name "htmlFiles".
* Move the html pages that you saved previously to the htmlFiles directory.
* Run the <a href="https://github.com/samilkorkmaz/Get-YouTube-Links/blob/master/GetYouTubeVideoLinks.java">java program</a> to extract individual video links and save them to a text file called "allVideoLinks.txt"

<b>Raw notes:</b>
* I want something similar for my Picasa albums too. But right now, let's focus on YouTube.
* Depends on YouTube page design. Valid for 20.10.2015, might not work when YouTube changes its page design.
* Go to https://www.youtube.com/my_videos?o=U&pi=1
* Note total nb of videos (880 in my case)
* At each page, there are 30 videos. So, there will be 880/30 = 29.3 --> 30 pages
* for i=1 to 30, get url links that are of the form "watch?v=". Note that there are 90 such items on a page with 30 videos, i.e. every link is repeated 3 times. So, you take every third of such links.

Problem: Authentication. Workaround: Use MacroScheduler to simulate user interaction with Chrome:
* Open YouTube page in browser (which was authenticated before by the user by signing in to Google).
* Wait until page has finished loading, save page to a file, give it a name of "videos-n" where n is the number of the page.
* Go to next page, repeat
* Run html parser written in Java which takes the saved html files and outputs video links as a text file.

TODO:
* In Macro Scheduler, set save path and clean it.
* Create a similar repo for Picasa album links
