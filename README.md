Application reads .osm xml file and fills SQLite database with map objects from it. Information about how to draw this objects, its description, etc stores in XML files in /drawSettings/ folder and uses while converting from .osm to database (for optimization) and for drawing. Also database stores "ready for drawing" description of objects and tags, linked with every object on map. 

![Screenshot](https://github.com/pgalex/OSMViewer/raw/master/screenshots/screenshot.png)
