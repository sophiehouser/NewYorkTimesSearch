# Project 2 - *NewYorkTimesSearch*

**NewYorkTimesSearch** is an android app that allows a user to search for articles on web using simple filters. The app utilizes [New York Times Search API](http://developer.nytimes.com/docs/read/article_search_api_v2).

This was a project for an Android dev intensive I did through Facebook's FBU summer program.

Time spent: **20** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can **search for news article** by specifying a query and launching a search. Search displays a grid of image results from the New York Times Search API.
* [x] User can **scroll down to see more articles**. The maximum number of articles is limited by the API search.
* [x] User can tap on any image in results to see the full text of article **full-screen**

The following **optional** features are implemented:

* [x] Used the **ActionBar SearchView** or custom layout as the query box
* [x] User can **share an article link** to their friends or email it to themselves
* [ ] Improved the user interface and experiment with image assets and/or styling and coloring
* [x] User can click on "settings" which allows selection of **advanced search options** to filter results
  * [x] User can configure advanced search filters such as:
    * [x] Begin Date (using a date picker)
    * [ ] News desk values (Arts, Fashion & Style, Sports)
    * [x] Sort order (oldest or newest)
  * [x] Subsequent searches have any selected filters applied to the results
  * [x] Uses a lightweight modal dialog for filters rather than an activity (I think I did this for the datepicker)
* [ ] Replaces the default ActionBar with a [Toolbar](http://guides.codepath.com/android/Using-the-App-ToolBar).
* [ ] Apply the popular [Butterknife annotation library](http://guides.codepath.com/android/Reducing-View-Boilerplate-with-Butterknife) to reduce view boilerplate.
* [x] Replace `GridView` with the [RecyclerView](http://guides.codepath.com/android/Using-the-RecyclerView) and the `StaggeredGridLayoutManager` to improve the grid of image results displayed.
* [ ] Use Parcelable instead of Serializable leveraging the popular [Parceler library](http://guides.codepath.com/android/Using-Parceler).
* [ ] Replace Picasso with [Glide](http://inthecheesefactory.com/blog/get-to-know-glide-recommended-by-google/en) for more efficient image rendering.
* [ ] Before an article search is triggered by the user, displays the current top stories of the day by default.
* [ ] Hides the `Toolbar` at the top as the user scrolls down through the results using the [CoordinatorLayout and AppBarLayout](http://guides.codepath.com/android/Using-the-App-ToolBar#reacting-to-scroll).
* [ ] Leverage the popular [GSON library](http://guides.codepath.com/android/Using-Android-Async-Http-Client#decoding-with-gson-library) to streamline the parsing of JSON data and avoid manual parsing.


## Notes

I had issues running the app Friday afternoon because too many people had been using the NYT api from the Facebook network. 
This is an older commit to GitHub because when I tried to implement the news desk filter my whole app stopped working (I got as far 
as implementing the checkboxes and passing the information to the SearchActivity, but when I tried to filter the search by
the key words the app stopped working. Even when I commented out the code). I think my infinite scroll may have stopped working once I implimented the oldest/newest filter but I can't check because I'm having 
trouble loading articles. 

Since I can't run my app I won't be able to post a licecap video. 

I also know that the date picker incorrectly translates the month the user selects. So if the user selects June then the
date picker will show and save that as month 05 rather than 06. I would fix this by adding 1 to the myMonth variable. 

## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Picasso](http://square.github.io/picasso/) - Image loading and caching library for Android

## License

    Copyright [yyyy] [name of copyright owner]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
