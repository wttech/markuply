import Markuply from '@/templating/markuply';

import Hello from '@/test/Hello';
import '../css/app';

const templating = new Markuply();
templating.registerReact('hello', Hello);
templating.expose();
