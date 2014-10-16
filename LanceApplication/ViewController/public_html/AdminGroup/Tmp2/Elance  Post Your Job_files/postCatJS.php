function setSubcatOptions(catid) {
  var subcatlist = document.getElementById('subcatIdField');

  subcatlist.options.length = 0;
  catid = catid.replace(/^\s+|\s+$/g,"");
  if (catid == "") {
    subcatlist.options[0] = new Option('Please select a category first',' ');
  }
  
  if( catid == '10179' ) {  
  subcatlist.options[subcatlist.options.length] = new Option('Select Subcategory','');
  subcatlist.options[subcatlist.options.length] = new Option('Bulk Mailing','12535');
  subcatlist.options[subcatlist.options.length] = new Option('Community Moderation','14160');
  subcatlist.options[subcatlist.options.length] = new Option('Content Management','14161');
  subcatlist.options[subcatlist.options.length] = new Option('Customer Service','12536');
  subcatlist.options[subcatlist.options.length] = new Option('Customer Service Project Management','14162');
  subcatlist.options[subcatlist.options.length] = new Option('Data Entry','10194');
  subcatlist.options[subcatlist.options.length] = new Option('Event Planning','10199');
  subcatlist.options[subcatlist.options.length] = new Option('Fact Checking','12537');
  subcatlist.options[subcatlist.options.length] = new Option('Mailing List Development','12538');
  subcatlist.options[subcatlist.options.length] = new Option('Office Management','10200');
  subcatlist.options[subcatlist.options.length] = new Option('Other - Customer Service & Moderation','14163');
  subcatlist.options[subcatlist.options.length] = new Option('Presentation Formatting','10195');
  subcatlist.options[subcatlist.options.length] = new Option('Research','12540');
  subcatlist.options[subcatlist.options.length] = new Option('Social Network Management','14164');
  subcatlist.options[subcatlist.options.length] = new Option('Technical Support','14165');
  subcatlist.options[subcatlist.options.length] = new Option('Transcription','10196');
  subcatlist.options[subcatlist.options.length] = new Option('Travel Planning','10198');
  subcatlist.options[subcatlist.options.length] = new Option('Virtual Assistant','10243');
  subcatlist.options[subcatlist.options.length] = new Option('Word Processing','10197');
  subcatlist.options[subcatlist.options.length] = new Option('Other - Administrative Support','10201');
  }
  if( catid == '10184' ) {  
  subcatlist.options[subcatlist.options.length] = new Option('Select Subcategory','');
  subcatlist.options[subcatlist.options.length] = new Option('Animation','14126');
  subcatlist.options[subcatlist.options.length] = new Option('Art','14166');
  subcatlist.options[subcatlist.options.length] = new Option('Banner Ads','10232');
  subcatlist.options[subcatlist.options.length] = new Option('Brochures','10233');
  subcatlist.options[subcatlist.options.length] = new Option('Cartoons & Comics','12231');
  subcatlist.options[subcatlist.options.length] = new Option('Corporate Identity Kit','12504');
  subcatlist.options[subcatlist.options.length] = new Option('Design Project Management','14167');
  subcatlist.options[subcatlist.options.length] = new Option('Digital Image Editing','12507');
  subcatlist.options[subcatlist.options.length] = new Option('Emails & Newsletters','10213');
  subcatlist.options[subcatlist.options.length] = new Option('Graphic Design','10237');
  subcatlist.options[subcatlist.options.length] = new Option('Illustration','10230');
  subcatlist.options[subcatlist.options.length] = new Option('Label & Package Design','12506');
  subcatlist.options[subcatlist.options.length] = new Option('Logos','10231');
  subcatlist.options[subcatlist.options.length] = new Option('Mobile Design','14168');
  subcatlist.options[subcatlist.options.length] = new Option('Page & Book Design','12557');
  subcatlist.options[subcatlist.options.length] = new Option('Photography','14132');
  subcatlist.options[subcatlist.options.length] = new Option('Presentations','12558');
  subcatlist.options[subcatlist.options.length] = new Option('Stationery Design','10234');
  subcatlist.options[subcatlist.options.length] = new Option('Videos','14135');
  subcatlist.options[subcatlist.options.length] = new Option('Voice Talent','14131');
  subcatlist.options[subcatlist.options.length] = new Option('Other - Design','10238');
  subcatlist.options[subcatlist.options.length] = new Option('Other - Multimedia Services','14136');
  }
  if( catid == '14000' ) {  
  subcatlist.options[subcatlist.options.length] = new Option('Select Subcategory','');
  subcatlist.options[subcatlist.options.length] = new Option('3D Modelling','14169');
  subcatlist.options[subcatlist.options.length] = new Option('Architecture','14002');
  subcatlist.options[subcatlist.options.length] = new Option('CAD','14001');
  subcatlist.options[subcatlist.options.length] = new Option('Chemical Engineering','14170');
  subcatlist.options[subcatlist.options.length] = new Option('Civil & Structural','14003');
  subcatlist.options[subcatlist.options.length] = new Option('Contract Manufacturing','14102');
  subcatlist.options[subcatlist.options.length] = new Option('Electrical','14005');
  subcatlist.options[subcatlist.options.length] = new Option('Interior Design','14100');
  subcatlist.options[subcatlist.options.length] = new Option('Mechanical','14004');
  subcatlist.options[subcatlist.options.length] = new Option('Product Design','14101');
  subcatlist.options[subcatlist.options.length] = new Option('Other - Engineering & Manufacturing','10220');
  }
  if( catid == '10186' ) {  
  subcatlist.options[subcatlist.options.length] = new Option('Select Subcategory','');
  subcatlist.options[subcatlist.options.length] = new Option('Accounting','10245');
  subcatlist.options[subcatlist.options.length] = new Option('Billing & Collections','12565');
  subcatlist.options[subcatlist.options.length] = new Option('Financial Planning','10246');
  subcatlist.options[subcatlist.options.length] = new Option('Financial Reporting','12566');
  subcatlist.options[subcatlist.options.length] = new Option('HR Policies & Plans','12569');
  subcatlist.options[subcatlist.options.length] = new Option('Investment Analysis','14171');
  subcatlist.options[subcatlist.options.length] = new Option('Management Consulting','12200');
  subcatlist.options[subcatlist.options.length] = new Option('Outsourcing Consulting','12201');
  subcatlist.options[subcatlist.options.length] = new Option('Recruiting','14172');
  subcatlist.options[subcatlist.options.length] = new Option('Tax Services','14138');
  subcatlist.options[subcatlist.options.length] = new Option('Other - Finance & Mgmt','10248');
  }
  if( catid == '10183' ) {  
  subcatlist.options[subcatlist.options.length] = new Option('Select Subcategory','');
  subcatlist.options[subcatlist.options.length] = new Option('Blog Programming','10223');
  subcatlist.options[subcatlist.options.length] = new Option('Business Intelligence','14173');
  subcatlist.options[subcatlist.options.length] = new Option('Data Analysis','14174');
  subcatlist.options[subcatlist.options.length] = new Option('Data Engineering','14175');
  subcatlist.options[subcatlist.options.length] = new Option('Data Science','14176');
  subcatlist.options[subcatlist.options.length] = new Option('Database Administration','14177');
  subcatlist.options[subcatlist.options.length] = new Option('Database Development','10217');
  subcatlist.options[subcatlist.options.length] = new Option('Flash & Flex Animation','10228');
  subcatlist.options[subcatlist.options.length] = new Option('Mobile Applications','11033');
  subcatlist.options[subcatlist.options.length] = new Option('Other - Data Science','14178');
  subcatlist.options[subcatlist.options.length] = new Option('Product Management','14179');
  subcatlist.options[subcatlist.options.length] = new Option('Project Management','12552');
  subcatlist.options[subcatlist.options.length] = new Option('Search Engine Optimization','12515');
  subcatlist.options[subcatlist.options.length] = new Option('Software Application','10216');
  subcatlist.options[subcatlist.options.length] = new Option('Statistics','14180');
  subcatlist.options[subcatlist.options.length] = new Option('System Administration','10219');
  subcatlist.options[subcatlist.options.length] = new Option('Networking & Security','14139');
  subcatlist.options[subcatlist.options.length] = new Option('Technical Project Management','14181');
  subcatlist.options[subcatlist.options.length] = new Option('Technical Support','10218');
  subcatlist.options[subcatlist.options.length] = new Option('Testing & QA','14137');
  subcatlist.options[subcatlist.options.length] = new Option('User Experience Design','10227');
  subcatlist.options[subcatlist.options.length] = new Option('Web Programming','10224');
  subcatlist.options[subcatlist.options.length] = new Option('Website Design','10225');
  subcatlist.options[subcatlist.options.length] = new Option('Other IT & Programming','12350');
  }
  if( catid == '10187' ) {  
  subcatlist.options[subcatlist.options.length] = new Option('Select Subcategory','');
  subcatlist.options[subcatlist.options.length] = new Option('Bankruptcy','12260');
  subcatlist.options[subcatlist.options.length] = new Option('Business and Corporate','10255');
  subcatlist.options[subcatlist.options.length] = new Option('Contracts','10254');
  subcatlist.options[subcatlist.options.length] = new Option('Criminal','12261');
  subcatlist.options[subcatlist.options.length] = new Option('Family','12262');
  subcatlist.options[subcatlist.options.length] = new Option('Immigration','12263');
  subcatlist.options[subcatlist.options.length] = new Option('Incorporation','10256');
  subcatlist.options[subcatlist.options.length] = new Option('Internet Law','14182');
  subcatlist.options[subcatlist.options.length] = new Option('Labor & Employment Law','14183');
  subcatlist.options[subcatlist.options.length] = new Option('Landlord and Tenant','12264');
  subcatlist.options[subcatlist.options.length] = new Option('Litigation','12571');
  subcatlist.options[subcatlist.options.length] = new Option('Negligence','12265');
  subcatlist.options[subcatlist.options.length] = new Option('Other Legal Fields','14184');
  subcatlist.options[subcatlist.options.length] = new Option('Other Legal Services','14185');
  subcatlist.options[subcatlist.options.length] = new Option('Paralegal Services','14186');
  subcatlist.options[subcatlist.options.length] = new Option('Patent, Copyright and Trademarks','10251');
  subcatlist.options[subcatlist.options.length] = new Option('Personal Injury','12266');
  subcatlist.options[subcatlist.options.length] = new Option('Real Estate','12267');
  subcatlist.options[subcatlist.options.length] = new Option('Tax Law','12268');
  subcatlist.options[subcatlist.options.length] = new Option('Wills, Trusts and Estates','12508');
  subcatlist.options[subcatlist.options.length] = new Option('Other - Legal','10257');
  }
  if( catid == '10178' ) {  
  subcatlist.options[subcatlist.options.length] = new Option('Select Subcategory','');
  subcatlist.options[subcatlist.options.length] = new Option('Ad Campaigns','10724');
  subcatlist.options[subcatlist.options.length] = new Option('Business Plans','10189');
  subcatlist.options[subcatlist.options.length] = new Option('Email Marketing','12503');
  subcatlist.options[subcatlist.options.length] = new Option('Lead Generation','12526');
  subcatlist.options[subcatlist.options.length] = new Option('Marketing & Sales Consulting','10188');
  subcatlist.options[subcatlist.options.length] = new Option('Marketing Project Management','14187');
  subcatlist.options[subcatlist.options.length] = new Option('Marketing Strategy','10190');
  subcatlist.options[subcatlist.options.length] = new Option('Product Marketing ','14188');
  subcatlist.options[subcatlist.options.length] = new Option('Public Relations Consulting','10192');
  subcatlist.options[subcatlist.options.length] = new Option('Research & Surveys','12527');
  subcatlist.options[subcatlist.options.length] = new Option('Sales Presentations','12532');
  subcatlist.options[subcatlist.options.length] = new Option('Search Engine Marketing','12529');
  subcatlist.options[subcatlist.options.length] = new Option('Telemarketing','12502');
  subcatlist.options[subcatlist.options.length] = new Option('Training','10221');
  subcatlist.options[subcatlist.options.length] = new Option('Viral Marketing','12524');
  subcatlist.options[subcatlist.options.length] = new Option('Other - Sales & Marketing','10193');
  }
  if( catid == '10180' ) {  
  subcatlist.options[subcatlist.options.length] = new Option('Select Subcategory','');
  subcatlist.options[subcatlist.options.length] = new Option('Academic Writing','12541');
  subcatlist.options[subcatlist.options.length] = new Option('Arabic <-> English Translation','14189');
  subcatlist.options[subcatlist.options.length] = new Option('Article Writing','10204');
  subcatlist.options[subcatlist.options.length] = new Option('Children\'s Writing','12543');
  subcatlist.options[subcatlist.options.length] = new Option('Chinese <-> English Translation','14190');
  subcatlist.options[subcatlist.options.length] = new Option('Copywriting','10205');
  subcatlist.options[subcatlist.options.length] = new Option('Creative Writing','10209');
  subcatlist.options[subcatlist.options.length] = new Option('E-books and Blogs','12522');
  subcatlist.options[subcatlist.options.length] = new Option('Editing & Proofreading','10206');
  subcatlist.options[subcatlist.options.length] = new Option('German <-> English Translation','14191');
  subcatlist.options[subcatlist.options.length] = new Option('Ghost Writing','12544');
  subcatlist.options[subcatlist.options.length] = new Option('Grant Writing','12516');
  subcatlist.options[subcatlist.options.length] = new Option('Japanese <-> English Translation','14192');
  subcatlist.options[subcatlist.options.length] = new Option('Newsletters','12547');
  subcatlist.options[subcatlist.options.length] = new Option('Other - Translation','14193');
  subcatlist.options[subcatlist.options.length] = new Option('Portuguese <-> English Translation','14194');
  subcatlist.options[subcatlist.options.length] = new Option('Press Releases','10203');
  subcatlist.options[subcatlist.options.length] = new Option('Report Writing','12550');
  subcatlist.options[subcatlist.options.length] = new Option('Resumes & Cover Letters','10240');
  subcatlist.options[subcatlist.options.length] = new Option('Russian <-> English Translation','14195');
  subcatlist.options[subcatlist.options.length] = new Option('Sales Writing','12548');
  subcatlist.options[subcatlist.options.length] = new Option('Spanish <-> English Translation','14196');
  subcatlist.options[subcatlist.options.length] = new Option('Speeches','12549');
  subcatlist.options[subcatlist.options.length] = new Option('Technical Writing','10207');
  subcatlist.options[subcatlist.options.length] = new Option('Translation','10202');
  subcatlist.options[subcatlist.options.length] = new Option('Translation Project Management','14197');
  subcatlist.options[subcatlist.options.length] = new Option('User Guides & Manuals','12546');
  subcatlist.options[subcatlist.options.length] = new Option('Web Content','10208');
  subcatlist.options[subcatlist.options.length] = new Option('Writing Project Management','14198');
  subcatlist.options[subcatlist.options.length] = new Option('Other - Writing Services','10210');
  }
}