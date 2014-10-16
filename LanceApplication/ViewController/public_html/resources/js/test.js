var data=[
            {
                "id" : 1023,
                "title" : "Article and Content Project",
                "type" : "Project",
                "description" : "Use this guide to create high-quality content for your business covering a range of topics and formats, including blog articles, news articles, instructional, and more. "
            },
            {
                "id" : 1024,
                "title" : "Article Writer",
                "type" : "Position",
                "description" : "Hire an Article Writer to leverage high-quality written content to develop your business' messaging, SEO strategy and more."
            },
            {
                "id" : 1025,
                "title" : "Blogger",
                "type" : "Position",
                "description" : "Hire a Blogger using this template to create keyword-rich content that will keep your audience engaged and returning to your website."
            },
            {
                "id" : 1103,
                "title" : "Business Writer",
                "type" : "Position",
                "description" : "Use this template to hire a Business Writer to create effective content designed for a business environment."
            },
            {
                "id" : 2044,
                "title" : "Business Writing Project",
                "type" : "Project",
                "description" : "Use this template to hire an expert to write your press releases, sales copy, or other business related copy."
            },
            {
                "id" : 1019,
                "title" : "Copywriter",
                "type" : "Position",
                "description" : "Find a Copywriter to create high-quality content for your website with this job description."
            },
            {
                "id" : 1102,
                "title" : "Creative Writer",
                "type" : "Position",
                "description" : "Use this template to hire a creative writer to develop your fiction writing story as well as characters, environments and events."
            },
            {
                "id" : 1104,
                "title" : "Editor",
                "type" : "Position",
                "description" : "Use this template to hire an Editor to develop your content themes and elevate your content to the next level."
            },
            {
                "id" : 1105,
                "title" : "Ghostwriter",
                "type" : "Position",
                "description" : "Use this template to hire a Ghostwriter to develop high-quality content for your business on your behalf."
            },
            {
                "id" : 2077,
                "title" : "hibu Job Template",
                "type" : "Project",
                "description" : "Use this Job Template for hibu copywriting jobs"
            },
            {
                "id" : 1101,
                "title" : "Marketing Writer",
                "type" : "Position",
                "description" : "Hire a Marketing Writer to develop high-quality content to effectively communicate your messaging to your audience.\n"
            },
            {
                "id" : 2046,
                "title" : "Other Writing & Translation Project",
                "type" : "Project",
                "description" : "Use this template to find an expert for your writing or translation needs."
            },
            {
                "id" : 2045,
                "title" : "Other Writing and Translation Project",
                "type" : "Project",
                "description" : ""
            },
            {
                "id" : 1107,
                "title" : "Press Release Project",
                "type" : "Project",
                "description" : "This template will help you generate a high-quality Press Release to announce your new features, products and more."
            },
            {
                "id" : 1121,
                "title" : "Proofreading and Editing Project",
                "type" : "Project",
                "description" : "Use this guide to have your documents and written materials edited for grammar, voice and more to fit your business needs."
            },
            {
                "id" : 1106,
                "title" : "Technical Writer",
                "type" : "Position",
                "description" : "Use this template to hire a Technical Writer to help create documentation, manuals and more for your business."
            },
            {
                "id" : 1022,
                "title" : "Translation",
                "type" : "Project",
                "description" : "Use this guide to take your existing document and have it translated into any language your business needs."
            }
        ];
        
var str="";
for(var i =0 ;i< data.length;i++){
var d=data[i];
var f1=d.title.replace("'","\'");
var f2=d.description.replace("'","\''");
f2=f2.replace("&","'||'&'||'");
var f3=(d.type=="Position"?"1":"2");
console.log(d.id);
str+="insert into JOB_TEMPLATE (UUID,JOB_CATEGORY_ID,NAME_EN,TYPE,TIPS) VALUES ('"+d.id+"','10180','"+f1+"','"+f3+"','"+f2+"');\n"
}
console.log(str);        
        