# GetYoutTubeLinks
Get links to all your uploaded videos on YouTube. It works like this:
* First, save all video list pages as html files to local disk by using the Macro Scheduler 14 script (getYouTubeLinks.scp)
* Run the java program (GetYouTubeLinks.java) to extract individual video links and save them to a text file.

Raw notes:

Get URL links of all your uploaded YouTube videos:
* I upload my videos with the privacy setting of "Unlisted" which means that only people who have the link to a video can watch it. I need to share those links with a small number of people my wife and parents. Currently, there is no way to do that on YouTube. Sharing links manually is tedious. I wanted a program that would go to my YouTube page, extract the links of all my uploads so that I can copy those links and send a single email to the people with whom I want to share my videos. I want something similar for my Picasa albums too. But right now, let's focus on YouTube.
* Depends on YouTube page design. Valid for 20.10.2015, might not work when YouTube changes its page design.
* Go to https://www.youtube.com/my_videos?o=U&pi=1
* Get total nb of videos from <span id="creator-subheader-item-count" class="yt-badge-creator">880</span>
* At each page, there are 30 videos. Soo there will be 880/30 = 29.3 --> 30 pages
* for i=1 to 30, get url links that are of the form "watch?v=". Note that there are 90 such items on a page with 30 videos, i.e. every link is repeated 3 times. So, you take every third of such links.

Problem: Authentication. Workaround: Use MacroScheduler:
* Open YouTube page in browser (which was authenticated before by the user by signing in to Google).
* Wait until page has finished loading, save page to a file, give it a name of "videos-n" where n is the number of the page.
* Go to next page, repeat
* Run html parser written in Java which takes the saved html files and outputs video links as a text file.

TODO:
* In Macro Scheduler, set save path and clean it.
