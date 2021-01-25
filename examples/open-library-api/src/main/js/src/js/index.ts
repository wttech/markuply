import Markuply from '@/templating/markuply';

import Accordion from '@/accordion/Accordion';
import Hello from '@/test/Hello';
import BookDetails from "@/book/BookDetails";
import BookMetadata from "@/book/BookMetadata";
import '../css/app';
import '../css/page';

const templating = new Markuply();

templating.registerReact('hello', Hello);
templating.registerReact('accordion', Accordion);
templating.registerReact('bookMetadata', BookMetadata);
templating.registerReact('bookDetails', BookDetails);

templating.expose();
