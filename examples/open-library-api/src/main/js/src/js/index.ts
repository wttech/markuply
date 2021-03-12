import GraalBridgeReact from '@wttech/graal-bridge-react';

import Accordion from '@/accordion/Accordion';
import Hello from '@/test/Hello';
import BookDetails from "@/book/BookDetails";
import BookMetadata from "@/book/BookMetadata";
import '../css/app';
import '../css/page';

const templating = new GraalBridgeReact();

templating.registerReact('hello', Hello);
templating.registerReact('accordion', Accordion);
templating.registerReact('bookMetadata', BookMetadata);
templating.registerReact('bookDetails', BookDetails);

templating.expose();
